package com.salyvn.omnigemlevel.service

import com.salyvn.omnigemlevel.OmniGemLevelPlugin
import com.salyvn.omnigemlevel.model.GemTemplateRef
import com.salyvn.omnigemlevel.stat.GemIdLevelData
import com.salyvn.omnigemlevel.stat.GemIdLevelStat
import io.lumine.mythic.lib.api.item.NBTItem
import net.Indyuce.mmoitems.ItemStats
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.Type
import net.Indyuce.mmoitems.api.interaction.GemStone
import net.Indyuce.mmoitems.api.item.mmoitem.LiveMMOItem
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem
import net.Indyuce.mmoitems.api.player.PlayerData
import net.Indyuce.mmoitems.stat.data.GemstoneData
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GemUpgradeService(
    private val plugin: OmniGemLevelPlugin,
    private val stat: GemIdLevelStat,
    private val resolver: GemTemplateResolver
) {
    fun hasMatchingAppliedGem(target: ItemStack, cursorData: GemIdLevelData): Boolean {
        val targetMmo = loadTarget(target) ?: return false
        return findMatchingGem(targetMmo, cursorData) != null
    }

    fun upgrade(player: Player, target: ItemStack, cursorData: GemIdLevelData): UpgradeOutcome {
        val targetNbt = NBTItem.get(target)
        val targetType = Type.get(targetNbt)
            ?: return UpgradeOutcome.Failure("upgrade-failed", mapOf())
        val targetMmo = loadTarget(target)
            ?: return UpgradeOutcome.Failure("upgrade-failed", mapOf())
        val match = findMatchingGem(targetMmo, cursorData)
            ?: return UpgradeOutcome.Failure("upgrade-failed", mapOf())

        val nextLevel = cursorData.level + 1
        val maxLevel = resolver.maxLevel(cursorData.gemId)
        if (nextLevel > maxLevel) {
            return UpgradeOutcome.Failure(
                "max-level",
                mapOf(
                    "gem" to cursorData.gemId,
                    "level" to cursorData.level.toString()
                )
            )
        }

        val template = resolver.resolve(cursorData.gemId, nextLevel, match.gem.getMMOItemType() ?: "GEM_STONE")
            ?: return UpgradeOutcome.Failure(
                "missing-template",
                mapOf("gem" to cursorData.gemId, "level" to nextLevel.toString())
            )

        val nextGem = buildTemplateItem(template)
            ?: return UpgradeOutcome.Failure(
                "missing-template",
                mapOf("gem" to cursorData.gemId, "level" to nextLevel.toString())
            )

        val nextData = readGemIdLevel(nextGem)
        if (nextData == null || !nextData.sameGemAndLevel(cursorData.gemId, nextLevel)) {
            return UpgradeOutcome.Failure(
                "invalid-template",
                mapOf("gem" to cursorData.gemId, "level" to nextLevel.toString())
            )
        }

        removeGemAndBonuses(targetMmo, match.gem)

        val applyResult = GemStone(PlayerData.get(player), NBTItem.get(nextGem))
            .applyOntoItem(targetMmo, targetType, target.itemMeta?.displayName ?: target.type.name, true, true)

        val upgradedItem = applyResult.result
        if (applyResult.type != GemStone.ResultType.SUCCESS || upgradedItem == null) {
            return UpgradeOutcome.Failure(
                "upgrade-failed",
                mapOf("gem" to cursorData.gemId, "level" to nextLevel.toString())
            )
        }

        return UpgradeOutcome.Success(
            upgradedItem = upgradedItem,
            gemId = cursorData.gemId,
            oldLevel = cursorData.level,
            newLevel = nextLevel
        )
    }

    private fun loadTarget(item: ItemStack): LiveMMOItem? {
        if (item.type.isAir) return null
        val nbt = NBTItem.get(item)
        if (!nbt.hasType()) return null
        if (!nbt.hasTag(ItemStats.GEM_SOCKETS.nbtPath)) return null
        return try {
            LiveMMOItem(nbt)
        } catch (ex: Exception) {
            plugin.logger.warning("Could not load target MMOItem: ${ex.message}")
            null
        }
    }

    private fun findMatchingGem(target: MMOItem, cursorData: GemIdLevelData): AppliedGem? {
        return target.gemstones.firstNotNullOfOrNull { gem ->
            val data = readGemIdLevel(gem)
            if (data != null && data.sameGemAndLevel(cursorData.gemId, cursorData.level)) AppliedGem(gem, data) else null
        }
    }

    private fun readGemIdLevel(gem: GemstoneData): GemIdLevelData? {
        val typeId = gem.getMMOItemType() ?: return null
        val itemId = gem.getMMOItemID() ?: return null
        val type = MMOItems.plugin.types.get(typeId) ?: return null
        val mmoItem = MMOItems.plugin.getMMOItem(type, itemId) ?: return null
        return mmoItem.getData(stat) as? GemIdLevelData
    }

    private fun readGemIdLevel(item: ItemStack): GemIdLevelData? =
        GemIdLevelStat.readFromNbt(NBTItem.get(item))

    private fun buildTemplateItem(template: GemTemplateRef): ItemStack? {
        val type = MMOItems.plugin.types.get(template.type) ?: return null
        return MMOItems.plugin.getItem(type, template.id)
    }

    private fun removeGemAndBonuses(target: MMOItem, gem: GemstoneData) {
        val gemId = gem.getHistoricUUID()
        target.removeGemStone(gemId, gem.getSocketColor())

        target.statHistories.toList().forEach { history ->
            if (history.getGemstoneData(gemId) != null) {
                history.removeGemData(gemId)
                val recalculated = history.recalculate(target.upgradeLevel)
                if (recalculated.isEmpty) target.removeData(history.itemStat) else target.setData(history.itemStat, recalculated)
            }
        }

        if (target.hasData(ItemStats.GEM_SOCKETS)) {
            val socketsHistory = target.computeStatHistory(ItemStats.GEM_SOCKETS)
            target.setData(ItemStats.GEM_SOCKETS, socketsHistory.recalculate(target.upgradeLevel))
        }
    }

    private fun GemIdLevelData.sameGemAndLevel(gemId: String, level: Int): Boolean =
        this.gemId.equals(gemId, ignoreCase = true) && this.level == level

    private data class AppliedGem(
        val gem: GemstoneData,
        val data: GemIdLevelData
    )

    sealed class UpgradeOutcome {
        data class Success(
            val upgradedItem: ItemStack,
            val gemId: String,
            val oldLevel: Int,
            val newLevel: Int
        ) : UpgradeOutcome()

        data class Failure(
            val messageKey: String,
            val placeholders: Map<String, String>
        ) : UpgradeOutcome()
    }
}
