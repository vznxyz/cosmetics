package net.evilblock.cosmetics.command

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cubed.command.Command
import org.bukkit.ChatColor
import org.bukkit.entity.Player

object CosmeticsCommand {

    @Command(
        names = ["cosmetics"],
        description = "Access your cosmetics"
    )
    @JvmStatic
    fun execute(player: Player) {
        CosmeticsPlugin.instance.hook?.also { hook ->
            if (!hook.isPlayerInSupportedRegion(player)) {
                player.sendMessage("${ChatColor.RED}You can't access your cosmetics in this region!")
                return
            }
        }

        CosmeticsPlugin.instance.openMenu(player)
    }

}