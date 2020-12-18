package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cosmetics.category.emote.EmotesBoxCosmetic
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.BackButton
import org.bukkit.entity.Player

class EmoteBoxMenu : Menu() {

    override fun getTitle(player: Player): String {
        return "Emote Box"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            buttons[0] = BackButton { CosmeticsPlugin.instance.hook.openMainMenu(player) }

            for ((index, emote) in EmotesBoxCosmetic.emotes.withIndex()) {
                buttons[index] = EmoteButton(emote)
            }
        }
    }

}