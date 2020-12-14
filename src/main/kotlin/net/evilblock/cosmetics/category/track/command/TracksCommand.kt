package net.evilblock.cosmetics.category.track.command

import net.evilblock.cosmetics.category.track.menu.TracksMenu
import net.evilblock.cubed.command.Command
import org.bukkit.entity.Player

object TracksCommand {

    @Command(
            names = ["tracks", "track"],
            description = "Open the Tracks menu"
    )
    @JvmStatic
    fun execute(player: Player) {
        TracksMenu().openMenu(player)
    }

}