package net.evilblock.cosmetics.profile.listener

import net.evilblock.cosmetics.category.track.util.TrackUtil
import net.evilblock.cosmetics.profile.ProfileHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerTeleportEvent

object ProfileTrackListeners : Listener {

    @EventHandler
    fun onPlayerTeleportEvent(event: PlayerTeleportEvent) {
        val track = ProfileHandler.getProfile(event.player)?.track
        if (track != null) {
            if (event.to.world != event.from.world) {
                track.clearChanges()
            } else {
                if (event.to.distance(event.from) >= 12) {
                    track.clearChanges()
                }
            }
        }
    }

    @EventHandler
    fun onPlayerMoveEvent(event: PlayerMoveEvent) {
        if (event.from.blockX != event.to.blockX || event.from.blockZ != event.to.blockZ) {
            val track = ProfileHandler.getProfile(event.player)?.track
            if (track != null) {
                for (location in TrackUtil.getNewTrail(event.to.clone().add(0.0, -1.0, 0.0), false, track.track.radius)) {
                    track.addBlockChange(location)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        ProfileHandler.getProfile(event.player)?.track?.clearChanges()
    }

}