package com.salyvn.omnigemlevel.stat

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GemIdLevelDataTest {
    @Test
    fun `parses simple gem id and level`() {
        val data = GemIdLevelData.parse("damage_3")

        assertEquals("damage", data.gemId)
        assertEquals(3, data.level)
        assertEquals("damage_3", data.raw)
    }

    @Test
    fun `parses gem id containing underscores`() {
        val data = GemIdLevelData.parse("damage_bonus_4")

        assertEquals("damage_bonus", data.gemId)
        assertEquals(4, data.level)
    }

    @Test
    fun `rejects missing underscore`() {
        assertFailsWith<IllegalArgumentException> {
            GemIdLevelData.parse("damage")
        }
    }

    @Test
    fun `rejects non numeric level`() {
        assertFailsWith<IllegalArgumentException> {
            GemIdLevelData.parse("damage_x")
        }
    }

    @Test
    fun `rejects zero level`() {
        assertFailsWith<IllegalArgumentException> {
            GemIdLevelData.parse("damage_0")
        }
    }
}
