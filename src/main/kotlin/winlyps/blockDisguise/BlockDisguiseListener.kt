package winlyps.blockDisguise

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent

class BlockDisguiseListener(private val blockDisguiseManager: BlockDisguiseManager) : Listener {

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player
        val armorStand = blockDisguiseManager.getDisguise(player)
        if (armorStand != null) {
            armorStand.teleport(player.location)
        }
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        blockDisguiseManager.removeDisguise(player)
    }
}