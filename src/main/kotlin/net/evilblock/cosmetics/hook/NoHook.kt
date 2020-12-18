package net.evilblock.cosmetics.hook

import net.evilblock.cosmetics.menu.CategoriesMenu
import org.bukkit.entity.Player

class NoHook : Hook {

    override fun openMainMenu(player: Player) {
        CategoriesMenu().openMenu(player)
    }

    override fun inSupportedRegion(player: Player): Boolean {
        return true
    }

    override fun canRenderEffects(player: Player): Boolean {
        return true
    }

    override fun canRenderTracks(player: Player): Boolean {
        return true
    }

}