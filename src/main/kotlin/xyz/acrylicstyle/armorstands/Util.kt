package xyz.acrylicstyle.armorstands

import net.minecraft.server.v1_16_R3.Packet
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player

object Util {
    fun Location.addY(y: Double) = this.apply { this.y += y }
    fun Location.addX(x: Double) = this.apply { this.x += x }
    fun Packet<*>.sendTo(vararg players: Player) {
        players.map { (it as CraftPlayer).handle.playerConnection }.forEach { it.sendPacket(this) }
    }

    private val armorStandData = KVMap<Player, PlayerArmorStandData> {
        val firstFrame = ArmorStandsPlugin.frames[0]
        PlayerArmorStandData(it, firstFrame.width, firstFrame.height)
    }

    fun Player.getArmorStandData() = armorStandData[this]
    fun Player.unregisterArmorStandData() { armorStandData.remove(this)?.removed = true }
}
