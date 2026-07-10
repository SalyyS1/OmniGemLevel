package com.salyvn.omnigemlevel.service

import com.salyvn.omnigemlevel.config.GemLevelConfig
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class GemTemplateResolverTest {
    @Test
    fun `config map resolves exact type and id`() {
        val resolver = resolver(
            gems = mapOf(
                "damage" to GemLevelConfig.GemEntry(
                    type = "GEM_STONE",
                    maxLevel = 5,
                    levels = mapOf(2 to "DAMAGE_TWO")
                )
            )
        )

        val ref = resolver.resolve("damage", 2)

        assertEquals("GEM_STONE", ref?.type)
        assertEquals("DAMAGE_TWO", ref?.id)
    }

    @Test
    fun `per gem max level overrides global default`() {
        val resolver = resolver(
            defaultMaxLevel = 5,
            gems = mapOf("damage" to GemLevelConfig.GemEntry("GEM_STONE", 3, emptyMap()))
        )

        assertEquals(3, resolver.maxLevel("damage"))
    }

    @Test
    fun `global default max level is used when gem override missing`() {
        val resolver = resolver(defaultMaxLevel = 5)

        assertEquals(5, resolver.maxLevel("unknown"))
    }

    @Test
    fun `convention fallback uses configured case`() {
        val resolver = resolver(idCase = GemLevelConfig.IdCase.UPPER)

        val ref = resolver.resolve("damage", 4)

        assertEquals("DAMAGE_4", ref?.id)
    }

    @Test
    fun `disabled convention fallback returns null when map misses`() {
        val resolver = resolver(conventionFallback = false)

        assertNull(resolver.resolve("damage", 4))
    }

    private fun resolver(
        defaultMaxLevel: Int = 5,
        conventionFallback: Boolean = true,
        idCase: GemLevelConfig.IdCase = GemLevelConfig.IdCase.UPPER,
        gems: Map<String, GemLevelConfig.GemEntry> = emptyMap()
    ) = GemTemplateResolver(
        GemLevelConfig.Snapshot(
            defaultMaxLevel = defaultMaxLevel,
            conventionFallback = conventionFallback,
            idCase = idCase,
            gems = gems
        )
    )
}
