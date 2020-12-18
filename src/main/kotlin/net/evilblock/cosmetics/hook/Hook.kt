package net.evilblock.cosmetics.hook

import org.bukkit.entity.Player

interface Hook {

    fun openMainMenu(player: Player)

    fun inSupportedRegion(player: Player): Boolean

    fun canRenderEffects(player: Player): Boolean

    fun canRenderTracks(player: Player): Boolean

}