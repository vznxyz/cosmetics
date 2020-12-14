package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.category.hidden.EmotesBoxCosmetic
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import org.bukkit.entity.Player

class EmoteBoxMenu : Menu() {

    override fun getTitle(player: Player): String {
        return "Emote Box"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        var i = 0
        for (emote in EmotesBoxCosmetic.emotes) {
            buttons[i++] = EmoteButton(emote)
        }

        return buttons
    }

}