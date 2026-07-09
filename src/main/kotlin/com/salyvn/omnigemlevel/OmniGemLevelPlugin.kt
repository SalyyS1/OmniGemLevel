package com.salyvn.omnigemlevel

import com.salyvn.omnigemlevel.config.GemLevelConfig
import com.salyvn.omnigemlevel.listener.GemUpgradeListener
import com.salyvn.omnigemlevel.service.GemTemplateResolver
import com.salyvn.omnigemlevel.service.GemUpgradeService
import com.salyvn.omnigemlevel.stat.GemIdLevelStat
import net.Indyuce.mmoitems.MMOItems
import org.bukkit.plugin.java.JavaPlugin

class OmniGemLevelPlugin : JavaPlugin() {
    lateinit var gemLevelConfig: GemLevelConfig
        private set
    lateinit var gemIdLevelStat: GemIdLevelStat
        private set

    companion object {
        lateinit var instance: OmniGemLevelPlugin
            private set
    }

    override fun onLoad() {
        instance = this
        gemIdLevelStat = GemIdLevelStat()
        try {
            MMOItems.plugin.stats.register(gemIdLevelStat)
            logger.info("Registered MMOItems stat ${GemIdLevelStat.STAT_ID}")
        } catch (ex: Exception) {
            logger.severe("Failed to register ${GemIdLevelStat.STAT_ID}: ${ex.message}")
        }
    }

    override fun onEnable() {
        saveDefaultConfig()
        reloadLocalConfig()

        val resolver = GemTemplateResolver(gemLevelConfig.snapshot)
        val service = GemUpgradeService(this, gemIdLevelStat, resolver)
        server.pluginManager.registerEvents(GemUpgradeListener(this, gemIdLevelStat, service), this)

        logger.info("OmniGemLevel enabled")
    }

    fun reloadLocalConfig() {
        reloadConfig()
        gemLevelConfig = GemLevelConfig.from(config)
    }
}
