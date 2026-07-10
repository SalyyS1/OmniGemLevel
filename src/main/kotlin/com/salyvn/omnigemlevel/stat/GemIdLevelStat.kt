package com.salyvn.omnigemlevel.stat

import io.lumine.mythic.lib.api.item.ItemTag
import io.lumine.mythic.lib.api.item.NBTItem
import io.lumine.mythic.lib.api.item.SupportedNBTTagValues
import net.Indyuce.mmoitems.MMOItems
import net.Indyuce.mmoitems.api.edition.StatEdition
import net.Indyuce.mmoitems.api.item.build.ItemStackBuilder
import net.Indyuce.mmoitems.api.item.mmoitem.ReadMMOItem
import net.Indyuce.mmoitems.gui.edition.EditionInventory
import net.Indyuce.mmoitems.stat.type.GemStoneStat
import net.Indyuce.mmoitems.stat.type.ItemStat
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.Optional

class GemIdLevelStat : ItemStat<GemIdLevelRandomData, GemIdLevelData>(
    STAT_ID,
    Material.EMERALD,
    "Gem ID Level",
    arrayOf(
        "Stores a logical gem id and level.",
        "Format: damage_3"
    ),
    arrayOf("gem_stone")
), GemStoneStat {

    override fun whenInitialized(obj: Any?): GemIdLevelRandomData =
        GemIdLevelRandomData(GemIdLevelData.parse(obj?.toString()))

    override fun whenApplied(item: ItemStackBuilder, data: GemIdLevelData) {
        if (!data.isEmpty) item.addItemTag(ItemTag(NBT_KEY, data.raw))
    }

    override fun getAppliedNBT(data: GemIdLevelData): ArrayList<ItemTag> =
        if (data.isEmpty) arrayListOf() else arrayListOf(ItemTag(NBT_KEY, data.raw))

    override fun whenClicked(inv: EditionInventory, event: InventoryClickEvent) {
        if (event.isRightClick) {
            inv.editedSection.set(path, null)
            inv.registerTemplateEdition()
            inv.player.sendMessage("${ChatColor.GREEN}Removed Gem ID Level.")
            return
        }

        StatEdition(inv, this).enable(
            "Write the gem id and level.",
            "Format: damage_3"
        )
    }

    override fun whenInput(inv: EditionInventory, message: String, vararg info: Any?) {
        val data = try {
            GemIdLevelData.parse(message)
        } catch (ex: IllegalArgumentException) {
            inv.player.sendMessage("${ChatColor.RED}${ex.message}")
            return
        }

        inv.editedSection.set(path, if (data.isEmpty) null else data.raw)
        inv.registerTemplateEdition()
        inv.player.sendMessage("${ChatColor.GREEN}Set Gem ID Level to ${ChatColor.WHITE}${data.raw}${ChatColor.GREEN}.")
    }

    override fun whenLoaded(mmoitem: ReadMMOItem) {
        val data = readFromNbt(mmoitem.nbt) ?: return
        mmoitem.setData(this, data)
    }

    override fun getLoadedNBT(storedTags: ArrayList<ItemTag>): GemIdLevelData? {
        val raw = storedTags.firstOrNull { it.path == NBT_KEY }?.value as? String ?: return null
        return try {
            GemIdLevelData.parse(raw)
        } catch (_: IllegalArgumentException) {
            null
        }
    }

    override fun whenDisplayed(lore: MutableList<String>, statData: Optional<GemIdLevelRandomData>) {
        val data = statData.map { it.data }.orElse(GemIdLevelData.EMPTY)
        if (data.isEmpty) {
            lore.add("  ${ChatColor.GRAY}Not set")
            lore.add("  ${ChatColor.YELLOW}Left-click to set.")
            lore.add("  ${ChatColor.YELLOW}Right-click to clear.")
            return
        }

        lore.add("  ${ChatColor.GRAY}Gem: ${ChatColor.WHITE}${data.gemId}")
        lore.add("  ${ChatColor.GRAY}Level: ${ChatColor.WHITE}${data.level}")
        lore.add("  ${ChatColor.YELLOW}Left-click to change.")
        lore.add("  ${ChatColor.YELLOW}Right-click to clear.")
    }

    override fun getClearStatData(): GemIdLevelData = GemIdLevelData.EMPTY

    companion object {
        const val STAT_ID = "GEM_ID_LEVEL"
        const val NBT_KEY = "MMOITEMS_GEM_ID_LEVEL"

        fun readFromNbt(nbt: NBTItem): GemIdLevelData? {
            if (!nbt.hasTag(NBT_KEY)) return null
            val tag = ItemTag.getTagAtPath(NBT_KEY, nbt, SupportedNBTTagValues.STRING)
            val raw = tag?.value as? String ?: nbt.getString(NBT_KEY)
            return try {
                GemIdLevelData.parse(raw)
            } catch (ex: IllegalArgumentException) {
                MMOItems.plugin.logger.warning("Invalid $STAT_ID value '$raw': ${ex.message}")
                null
            }
        }
    }
}
