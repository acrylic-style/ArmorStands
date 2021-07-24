package xyz.acrylicstyle.armorstands

import org.bukkit.ChatColor
import kotlin.math.abs

// chat colors as of 1.17
object ChatColors {
    const val BLACK = 0x000000
    const val DARK_BLUE = 0x0000AA
    const val DARK_GREEN = 0x00AA00
    const val DARK_AQUA = 0x00AAAA
    const val DARK_RED = 0xAA0000
    const val DARK_PURPLE = 0xAA00AA
    const val GOLD = 0xFFAA00
    const val GRAY = 0xAAAAAA
    const val DARK_GRAY = 0x555555
    const val BLUE = 0x5555FF
    const val GREEN = 0x55FF55
    const val AQUA = 0x55FFFF
    const val RED = 0xFF5555
    const val LIGHT_PURPLE = 0xFF55FF
    const val YELLOW = 0xFFFF55
    const val WHITE = 0xFFFFFF

    fun values() = mapOf(
        "BLACK" to BLACK,
        "DARK_BLUE" to DARK_BLUE,
        "DARK_GREEN" to DARK_GREEN,
        "DARK_AQUA" to DARK_AQUA,
        "DARK_RED" to DARK_RED,
        "DARK_PURPLE" to DARK_PURPLE,
        "GOLD" to GOLD,
        "GRAY" to GRAY,
        "DARK_GRAY" to DARK_GRAY,
        "BLUE" to BLUE,
        "GREEN" to GREEN,
        "AQUA" to AQUA,
        "RED" to RED,
        "LIGHT_PURPLE" to LIGHT_PURPLE,
        "YELLOW" to YELLOW,
        "WHITE" to WHITE,
    )

    fun getNearestChatColor(color: Int) = ChatColor.valueOf(values().entries.sortedWith { a, b -> abs(color - a.value) - abs(color - b.value) }.first().key)
}
