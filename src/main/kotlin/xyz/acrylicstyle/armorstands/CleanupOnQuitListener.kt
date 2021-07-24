package xyz.acrylicstyle.armorstands

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import xyz.acrylicstyle.armorstands.Util.unregisterArmorStandData

object CleanupOnQuitListener: Listener {
    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        e.player.unregisterArmorStandData()
    }
}
