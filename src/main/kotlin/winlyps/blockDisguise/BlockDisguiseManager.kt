package winlyps.blockDisguise

import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.HashMap

class BlockDisguiseManager(private val plugin: BlockDisguise) {

    private val disguises = HashMap<Player, ArmorStand>()

    fun setDisguise(player: Player, material: Material) {
        // Remove any existing disguise for the player
        resetDisguise(player)

        val location = player.location
        val armorStand = location.world?.spawnEntity(location, EntityType.ARMOR_STAND) as ArmorStand
        armorStand.isVisible = false
        armorStand.isMarker = true
        armorStand.equipment?.helmet = ItemStack(material)

        // Scale the armor stand to make the block appear larger
        armorStand.setSmall(false)
        armorStand.setArms(false)
        armorStand.setBasePlate(false)
        armorStand.setGravity(false)
        armorStand.setInvulnerable(true)
        armorStand.setCollidable(false)
        armorStand.setCustomNameVisible(false)
        armorStand.setCustomName(null)
        armorStand.setSilent(true)
        armorStand.setAI(false)
        armorStand.setCanPickupItems(false)
        armorStand.setRemoveWhenFarAway(false)
        armorStand.setPersistent(true)

        disguises[player] = armorStand

        player.gameMode = GameMode.SPECTATOR
        player.spectatorTarget = armorStand

        // Force the player to stand up and update their view
        player.isSneaking = false
        player.teleport(player.location)
    }

    fun resetDisguise(player: Player) {
        val armorStand = disguises[player]
        if (armorStand != null) {
            armorStand.remove()
            disguises.remove(player)
        }
        if (player.gameMode == GameMode.SPECTATOR) {
            player.spectatorTarget = null
        }
        player.gameMode = GameMode.SURVIVAL
        player.isSneaking = false
    }

    fun getDisguise(player: Player): ArmorStand? {
        return disguises[player]
    }

    fun removeDisguise(player: Player) {
        val armorStand = disguises[player]
        if (armorStand != null) {
            armorStand.remove()
            disguises.remove(player)
        }
        if (player.gameMode == GameMode.SPECTATOR) {
            player.spectatorTarget = null
        }
        player.gameMode = GameMode.SURVIVAL
        player.isSneaking = false
    }
}