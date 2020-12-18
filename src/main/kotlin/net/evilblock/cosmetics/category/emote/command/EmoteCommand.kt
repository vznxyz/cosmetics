package net.evilblock.cosmetics.category.emote.command

import net.evilblock.cosmetics.menu.EmoteBoxMenu
import net.evilblock.cubed.command.Command
import org.bukkit.entity.Player

object EmoteCommand {

    @Command(
            names = ["emote", "emotes"],
            description = "Open the Emotes GUI"
    )
    @JvmStatic
    fun execute(player: Player) {
        EmoteBoxMenu().openMenu(player)
    }

}