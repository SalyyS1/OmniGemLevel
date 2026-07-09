package com.salyvn.omnigemlevel.config

import org.bukkit.configuration.file.FileConfiguration

data class GemLevelConfig(
    val snapshot: Snapshot,
    val messages: Map<String, String>
) {
    data class Snapshot(
        val defaultMaxLevel: Int,
        val conventionFallback: Boolean,
        val idCase: IdCase,
        val gems: Map<String, GemEntry>
    )

    data class GemEntry(
        val type: String,
        val maxLevel: Int?,
        val levels: Map<Int, String>
    )

    enum class IdCase {
        UPPER,
        LOWER,
        PRESERVE
    }

    fun message(key: String): String = messages[key] ?: key

    companion object {
        fun from(config: FileConfiguration): GemLevelConfig {
            val gems = mutableMapOf<String, GemEntry>()
            val gemsSection = config.getConfigurationSection("gems")
            gemsSection?.getKeys(false)?.forEach { gemId ->
                val section = gemsSection.getConfigurationSection(gemId) ?: return@forEach
                val levelSection = section.getConfigurationSection("levels")
                val levels = mutableMapOf<Int, String>()
                levelSection?.getKeys(false)?.forEach { rawLevel ->
                    val level = rawLevel.toIntOrNull() ?: return@forEach
                    val itemId = levelSection.getString(rawLevel)?.trim().orEmpty()
                    if (itemId.isNotEmpty()) levels[level] = itemId
                }

                gems[gemId.lowercase()] = GemEntry(
                    type = section.getString("type", "GEM_STONE")!!.trim(),
                    maxLevel = if (section.contains("max-level")) section.getInt("max-level") else null,
                    levels = levels.toMap()
                )
            }

            val messages = mutableMapOf<String, String>()
            config.getConfigurationSection("messages")?.getKeys(false)?.forEach { key ->
                messages[key] = config.getString("messages.$key").orEmpty()
            }

            val idCase = when (config.getString("resolver.id-case", "upper")!!.lowercase()) {
                "lower" -> IdCase.LOWER
                "preserve" -> IdCase.PRESERVE
                else -> IdCase.UPPER
            }

            return GemLevelConfig(
                snapshot = Snapshot(
                    defaultMaxLevel = config.getInt("default-max-level", 5).coerceAtLeast(1),
                    conventionFallback = config.getBoolean("resolver.convention-fallback", true),
                    idCase = idCase,
                    gems = gems.toMap()
                ),
                messages = messages.toMap()
            )
        }
    }
}
