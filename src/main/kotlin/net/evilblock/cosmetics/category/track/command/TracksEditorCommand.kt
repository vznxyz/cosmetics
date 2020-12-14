package net.evilblock.cosmetics.category.track.command

import net.evilblock.cosmetics.category.track.menu.TrackEditorMenu
import net.evilblock.cosmetics.util.Permissions
import net.evilblock.cubed.command.Command
import org.bukkit.entity.Player

object TracksEditorCommand {

    @Command(
        names = ["tracks editor", "track editor"],
        description = "Opens the Track Editor",
        permission = Permissions.TRACKS_EDITOR
    )
    @JvmStatic
    fun execute(player: Player) {
        TrackEditorMenu().openMenu(player)
    }

}