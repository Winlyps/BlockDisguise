package winlyps.blockDisguise

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class BlockDisguiseCommand(private val blockDisguiseManager: BlockDisguiseManager) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(ChatColor.RED.toString() + "Only players can use this command!")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(ChatColor.RED.toString() + "Usage: /blockdis <nameoftheblock> or /blockdis reset")
            return true
        }

        if (args[0].equals("reset", ignoreCase = true)) {
            blockDisguiseManager.resetDisguise(sender)
            sender.sendMessage(ChatColor.GREEN.toString() + "Your block disguise has been reset.")
            return true
        }

        val material = Material.matchMaterial(args[0])
        if (material == null || !material.isBlock) {
            sender.sendMessage(ChatColor.RED.toString() + "Invalid block name!")
            return true
        }

        blockDisguiseManager.setDisguise(sender, material)
        sender.sendMessage(ChatColor.GREEN.toString() + "You are now disguised as a ${material.name}.")
        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<String>): List<String> {
        if (args.size == 1) {
            val suggestions = mutableListOf("reset")
            suggestions.addAll(Material.values().filter { it.isBlock }.map { it.name.toLowerCase() })
            return suggestions.filter { it.startsWith(args[0].toLowerCase()) }
        }
        return emptyList()
    }
}