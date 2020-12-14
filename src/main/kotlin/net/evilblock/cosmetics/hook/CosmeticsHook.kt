package net.evilblock.cosmetics.hook

import org.bukkit.entity.Player

interface CosmeticsHook {

    fun isPlayerInSupportedRegion(player: Player): Boolean

    fun canRenderTrails(player: Player): Boolean

    fun canRenderTracks(player: Player): Boolean

}