package net.evilblock.cosmetics.profile.listener

import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.profile.ProfileHandler
import net.evilblock.prisonaio.module.robot.cosmetic.CosmeticHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

object ProfileLoadListeners : Listener {

    @EventHandler
    fun onAsyncPlayerPreLoginEvent(event: AsyncPlayerPreLoginEvent) {
        ProfileHandler.loadProfile(event.uniqueId)
    }

    @EventHandler
    fun onPlayerJoinEvent(event: PlayerJoinEvent) {
        val profile = ProfileHandler.getProfile(event.player)
        if (profile != null) {
            if (profile.track != null) {
                profile.track!!.cachedPlayer = event.player
            }

            for (cosmetic in profile.getEnabledCosmetics()) {
                if (profile.isCosmeticEnabled(cosmetic) || !cosmetic.hasState()) {
                    cosmetic.onEnable(event.player)
                }
            }
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val profile = ProfileHandler.getProfile(event.player)
        if (profile != null) {
            for (cosmetic in CosmeticHandler.getRegisteredCosmetics()) {
                if (cosmetic is TickableCosmetic) {
                    cosmetic.ticks.remove(event.player.uniqueId)
                }
            }

            if (profile.track != null) {
                profile.track!!.cachedPlayer = null
            }

            ProfileHandler.forgetProfile(profile)
        }
    }

}