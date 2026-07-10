package com.salyvn.omnigemlevel.stat

import net.Indyuce.mmoitems.stat.data.type.StatData

data class GemIdLevelData(
    val gemId: String,
    val level: Int
) : StatData {
    val raw: String
        get() = if (isEmpty) "" else "${gemId}_$level"

    override fun isEmpty(): Boolean = gemId.isBlank() || level <= 0

    companion object {
        val EMPTY = GemIdLevelData("", 0)

        fun parse(raw: String?): GemIdLevelData {
            val value = raw?.trim().orEmpty()
            if (value.isEmpty()) return EMPTY

            val separator = value.lastIndexOf('_')
            require(separator > 0 && separator < value.lastIndex) {
                "Format must be <gem_id>_<level>, example: damage_3"
            }

            val id = value.substring(0, separator).trim()
            val level = value.substring(separator + 1).trim().toIntOrNull()
                ?: throw IllegalArgumentException("Gem level must be a number: $value")
            require(id.isNotEmpty()) { "Gem id cannot be empty" }
            require(level > 0) { "Gem level must be greater than 0" }

            return GemIdLevelData(id, level)
        }
    }
}
