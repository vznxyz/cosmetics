package net.evilblock.cosmetics.command

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cubed.command.Command
import org.bukkit.command.CommandSender

object ReloadCommand {

    @Command(
            names = ["cosmetics reload"],
            description = "Reloads the Cosmetics configuration",
            permission = "op"
    )
    @JvmStatic
    fun execute(sender: CommandSender) {
        CosmeticsPlugin.instance.reloadConfig()
    }

}