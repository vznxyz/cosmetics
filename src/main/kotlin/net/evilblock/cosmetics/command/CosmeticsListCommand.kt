package net.evilblock.cosmetics.command

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cubed.command.Command
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

object CosmeticsListCommand {

    @Command(
        names = ["cosmetics list"],
        description = "Lists all cosmetics and their permissions",
        permission = "op"
    )
    @JvmStatic
    fun execute(sender: CommandSender) {
        val allCosmetics = CosmeticsPlugin.instance.categories.map { it.getCosmetics() }.flatten()
        sender.sendMessage("${ChatColor.GOLD}${ChatColor.BOLD}Cosmetics: ${allCosmetics.joinToString { "${ChatColor.RESET}${it.getName()} ${ChatColor.GRAY}[${ChatColor.WHITE}${it.getPermission()}${ChatColor.GRAY}]" }}")
    }

}