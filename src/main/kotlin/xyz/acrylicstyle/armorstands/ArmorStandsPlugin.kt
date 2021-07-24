package xyz.acrylicstyle.armorstands

import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import xyz.acrylicstyle.armorstands.Util.getArmorStandData
import java.awt.image.BufferedImage
import java.lang.NumberFormatException
import kotlin.math.max

class ArmorStandsPlugin: JavaPlugin() {
    companion object {
        lateinit var instance: ArmorStandsPlugin
        val frames = ArrayList<BufferedImage>()
    }

    init {
        instance = this
    }

    override fun onEnable() {
        server.pluginManager.registerEvents(CleanupOnQuitListener, this)
        getCommand("aspawn")?.setExecutor { sender, _, _, _ ->
            if (sender !is Player) return@setExecutor true
            if (frames.isEmpty()) {
                sender.sendMessage("${ChatColor.RED}Frames aren't loaded. Please do /aload to load them.")
                return@setExecutor true
            }
            sender.getArmorStandData().spawnAll()
            return@setExecutor true
        }
        getCommand("aload")?.setExecutor { sender, _, _, _ ->
            if (sender !is Player) return@setExecutor true
            Thread {
                sender.sendMessage("${ChatColor.GREEN}Loading frames...")
                frames.clear()
                frames.addAll(ImageUtil.readFrames())
                sender.sendMessage("${ChatColor.GREEN}Loaded ${frames.size} frames")
            }.start()
            return@setExecutor true
        }
        getCommand("astart")?.setExecutor { sender, _, _, args ->
            if (sender !is Player) return@setExecutor true
            if (args.isEmpty()) {
                sender.sendMessage("${ChatColor.RED}/astart <fps: 1-120>")
                return@setExecutor true
            }
            val fps = try {
                Integer.parseInt(args[0])
            } catch (_: NumberFormatException) {
                sender.sendMessage("${ChatColor.RED}/astart <fps: 1-120>")
                return@setExecutor true
            }
            if (fps <= 0 || fps > 120) {
                sender.sendMessage("${ChatColor.RED}/astart <fps: 1-120>")
                return@setExecutor true
            }
            Thread {
                var currentFrame = 0
                val data = sender.getArmorStandData()
                val obj = Object()
                while (!data.removed && currentFrame - 1 < frames.size) {
                    val start = System.nanoTime()
                    val frame = frames[currentFrame++]
                    data.updateText(frame)
                    data.updateAll()
                    val end = System.nanoTime()
                    var dms = 16 - (end - start) / 1000000
                    var dns = 666666 - (end - start).toInt() % 1000000
                    if (dns < 0) {
                        dns += 1000000
                        dms--
                    }
                    if (dms >= 0 || dns >= 0)
                    obj.wait(max(0, dms), dns)
                }
            }.start()
            return@setExecutor true
        }
        getCommand("astop")?.setExecutor { sender, _, _, _ ->
            if (sender !is Player) return@setExecutor true
            return@setExecutor true
        }
    }
}
