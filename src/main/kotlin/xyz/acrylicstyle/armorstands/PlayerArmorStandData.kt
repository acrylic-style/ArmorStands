package xyz.acrylicstyle.armorstands

import net.md_5.bungee.api.ChatColor
import net.minecraft.server.v1_16_R3.EntityArmorStand
import org.bukkit.entity.Player
import xyz.acrylicstyle.armorstands.ArmorStandUtil.destroy
import xyz.acrylicstyle.armorstands.ArmorStandUtil.getUpdatePacket
import xyz.acrylicstyle.armorstands.ArmorStandUtil.sendPacket
import xyz.acrylicstyle.armorstands.ArmorStandUtil.setText
import xyz.acrylicstyle.armorstands.Util.addX
import xyz.acrylicstyle.armorstands.Util.addY
import xyz.acrylicstyle.armorstands.Util.sendTo
import java.awt.Color
import java.awt.image.BufferedImage

class PlayerArmorStandData(private val player: Player, width: Int, height: Int) {
    companion object {
        const val SQUARE = '\u2b1b'
    }

    @Volatile
    var removed = false

    private val armorStands = ArrayList<ArrayList<EntityArmorStand>>()

    init {
        val yLoc = player.location.clone()
        for (h in 0..height) {
            val wLoc = yLoc.clone()
            for (w in 0..width) {
                val hl = armorStands.getOrElse(h) { ArrayList<EntityArmorStand>().apply { armorStands.add(this) } }
                hl.add(ArmorStandUtil.createHologram(wLoc.addX(ArmorStandUtil.offset), "$SQUARE"))
            }
            yLoc.addY(ArmorStandUtil.offset)
        }
    }

    fun spawnAll() {
        if (removed) return
        armorStands.forEach { list ->
            list.forEach {
                it.sendPacket(player)
            }
        }
    }

    fun destroyAll() {
        if (removed) return
        armorStands.forEach { list ->
            list.forEach {
                it.destroy(player)
            }
        }
    }

    fun updateAll() {
        if (removed) return
        armorStands.forEach { list ->
            list.forEach {
                it.getUpdatePacket().sendTo(player)
            }
        }
    }

    fun updateText(img: BufferedImage) {
        if (removed) return
        for (y in 0..img.height) {
            for (x in 0..img.width) {
                val color = ChatColor.of(Color(img.getRGB(x, y)))
                armorStands[y][x].setText("$color$SQUARE")
            }
        }
    }
}
