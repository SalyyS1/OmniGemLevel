package com.salyvn.omnigemlevel.stat

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GemIdLevelDataTest {
    @Test
    fun `parses simple gem id and level`() {
        val data = GemIdLevelData.parse("satthuong_3")

        assertEquals("satthuong", data.gemId)
        assertEquals(3, data.level)
        assertEquals("satthuong_3", data.raw)
    }

    @Test
    fun `parses gem id containing underscores`() {
        val data = GemIdLevelData.parse("sat_thuong_4")

        assertEquals("sat_thuong", data.gemId)
        assertEquals(4, data.level)
    }

    @Test
    fun `rejects missing underscore`() {
        assertFailsWith<IllegalArgumentException> {
            GemIdLevelData.parse("satthuong")
        }
    }

    @Test
    fun `rejects non numeric level`() {
        assertFailsWith<IllegalArgumentException> {
            GemIdLevelData.parse("satthuong_x")
        }
    }

    @Test
    fun `rejects zero level`() {
        assertFailsWith<IllegalArgumentException> {
            GemIdLevelData.parse("satthuong_0")
        }
    }
}
