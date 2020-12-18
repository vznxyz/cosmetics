package net.evilblock.cosmetics.profile.listener

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.profile.ProfileHandler
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

            if (CosmeticsPlugin.instance.hook.inSupportedRegion(event.player)) {
                for (cosmetic in profile.getEnabledCosmetics()) {
                    if (profile.isCosmeticEnabled(cosmetic) || !cosmetic.hasState()) {
                        cosmetic.onEnable(event.player)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        val profile = ProfileHandler.getProfile(event.player)
        if (profile != null) {
            for (cosmetic in profile.getEnabledCosmetics()) {
                cosmetic.onDisable(event.player)

                if (cosmetic is TickableCosmetic) {
                    cosmetic.ticks.remove(event.player.uniqueId)
                }

                profile.requiresSave = true
            }

            if (profile.track != null) {
                profile.track!!.cachedPlayer = null
            }

            ProfileHandler.forgetProfile(profile)

            if (profile.requiresSave) {
                ProfileHandler.saveProfile(profile)
            }
        }
    }

}