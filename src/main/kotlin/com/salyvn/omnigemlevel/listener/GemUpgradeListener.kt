package com.salyvn.omnigemlevel.listener

import com.salyvn.omnigemlevel.OmniGemLevelPlugin
import com.salyvn.omnigemlevel.service.GemUpgradeService
import com.salyvn.omnigemlevel.stat.GemIdLevelData
import com.salyvn.omnigemlevel.stat.GemIdLevelStat
import io.lumine.mythic.lib.api.item.NBTItem
import net.Indyuce.mmoitems.api.Type
import net.Indyuce.mmoitems.api.event.item.ApplyGemStoneEvent
import net.Indyuce.mmoitems.api.interaction.GemStone
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.UUID

class GemUpgradeListener(
    private val plugin: OmniGemLevelPlugin,
    private val stat: GemIdLevelStat,
    private val service: GemUpgradeService
) : Listener {
    private val pending = mutableMapOf<UUID, PendingUpgrade>()
    private val internalApply = mutableSetOf<UUID>()

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    fun prepareUpgrade(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        if (event.action != InventoryAction.SWAP_WITH_CURSOR) return
        if (!player.hasPermission("omnigemlevel.use")) return

        val cursor = event.cursor ?: return
        val target = event.currentItem ?: return
        if (cursor.type.isAir || target.type.isAir) return

        val cursorData = readCursorGemData(cursor) ?: return
        if (!service.hasMatchingAppliedGem(target, cursorData)) return

        pending[player.uniqueId] = PendingUpgrade(event.rawSlot, cursorData)
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
    fun blockNativeDuplicateApply(event: ApplyGemStoneEvent) {
        if (event.player.uniqueId in internalApply) {
            event.result = GemStone.ResultType.SUCCESS
            return
        }
        if (pending.containsKey(event.player.uniqueId)) {
            event.result = GemStone.ResultType.NONE
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    fun completeUpgrade(event: InventoryClickEvent) {
        val player = event.whoClicked as? Player ?: return
        val intent = pending.remove(player.uniqueId) ?: return
        if (event.action != InventoryAction.SWAP_WITH_CURSOR || event.rawSlot != intent.rawSlot) return

        event.isCancelled = true

        if (!player.hasPermission("omnigemlevel.use")) {
            player.sendMessage(message("no-permission"))
            return
        }

        val target = event.currentItem
        if (target == null || target.type.isAir) {
            player.sendMessage(message("upgrade-failed"))
            return
        }

        val outcome = withInternalApply(player.uniqueId) {
            service.upgrade(player, target, intent.cursorData)
        }

        when (outcome) {
            is GemUpgradeService.UpgradeOutcome.Success -> {
                event.currentItem = outcome.upgradedItem
                consumeCursor(event)
                player.playSound(player.location, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.5f)
                player.sendMessage(
                    message(
                        "upgraded",
                        "gem" to outcome.gemId,
                        "old_level" to outcome.oldLevel.toString(),
                        "new_level" to outcome.newLevel.toString()
                    )
                )
            }
            is GemUpgradeService.UpgradeOutcome.Failure -> {
                player.playSound(player.location, Sound.ENTITY_VILLAGER_NO, 0.7f, 1f)
                player.sendMessage(message(outcome.messageKey, *outcome.placeholders.toList().toTypedArray()))
            }
        }
    }

    private fun readCursorGemData(cursor: org.bukkit.inventory.ItemStack): GemIdLevelData? {
        val nbt = NBTItem.get(cursor)
        val type = Type.get(nbt) ?: return null
        if (!type.corresponds(Type.GEM_STONE)) return null
        return GemIdLevelStat.readFromNbt(nbt)
    }

    private fun consumeCursor(event: InventoryClickEvent) {
        val cursor = event.cursor ?: return
        if (cursor.amount > 1) {
            cursor.amount -= 1
        } else {
            event.whoClicked.setItemOnCursor(null)
        }
    }

    private fun <T> withInternalApply(playerId: UUID, block: () -> T): T {
        internalApply.add(playerId)
        return try {
            block()
        } finally {
            internalApply.remove(playerId)
        }
    }

    private fun message(key: String, vararg placeholders: Pair<String, String>): String {
        var text = plugin.gemLevelConfig.message(key)
        placeholders.forEach { (name, value) -> text = text.replace("{$name}", value) }
        return ChatColor.translateAlternateColorCodes('&', text)
    }

    private data class PendingUpgrade(
        val rawSlot: Int,
        val cursorData: GemIdLevelData
    )
}
