package winlyps.blockDisguise

import org.bukkit.plugin.java.JavaPlugin

class BlockDisguise : JavaPlugin() {

    lateinit var blockDisguiseManager: BlockDisguiseManager

    override fun onEnable() {
        // Initialize the BlockDisguiseManager
        blockDisguiseManager = BlockDisguiseManager(this)

        // Register the command
        getCommand("blockdis")?.setExecutor(BlockDisguiseCommand(blockDisguiseManager))

        // Register the event listener
        server.pluginManager.registerEvents(BlockDisguiseListener(blockDisguiseManager), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}