package com.salyvn.omnigemlevel.service

import com.salyvn.omnigemlevel.config.GemLevelConfig
import com.salyvn.omnigemlevel.model.GemTemplateRef

class GemTemplateResolver(
    private val config: GemLevelConfig.Snapshot
) {
    fun maxLevel(gemId: String): Int =
        config.gems[gemId.normalized()]?.maxLevel?.coerceAtLeast(1) ?: config.defaultMaxLevel

    fun resolve(gemId: String, level: Int, fallbackType: String = "GEM_STONE"): GemTemplateRef? {
        if (level <= 0) return null
        val normalized = gemId.normalized()
        val entry = config.gems[normalized]
        val mappedId = entry?.levels?.get(level)
        if (!mappedId.isNullOrBlank()) {
            return GemTemplateRef(entry.type.ifBlank { fallbackType }, mappedId)
        }

        if (!config.conventionFallback) return null
        val conventionalId = when (config.idCase) {
            GemLevelConfig.IdCase.UPPER -> "${gemId}_$level".uppercase()
            GemLevelConfig.IdCase.LOWER -> "${gemId}_$level".lowercase()
            GemLevelConfig.IdCase.PRESERVE -> "${gemId}_$level"
        }
        return GemTemplateRef(entry?.type?.ifBlank { fallbackType } ?: fallbackType, conventionalId)
    }

    private fun String.normalized(): String = trim().lowercase()
}
