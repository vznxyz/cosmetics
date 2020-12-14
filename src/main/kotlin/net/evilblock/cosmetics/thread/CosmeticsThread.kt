package net.evilblock.cosmetics.thread

import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.profile.Profile
import net.evilblock.cosmetics.profile.ProfileHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CosmeticsThread : Thread("Cosmetics - Tick Thread") {

    override fun run() {
        while (true) {
            for (player in Bukkit.getOnlinePlayers()) {
                if (!player.isOnline) {
                    continue
                }

                val profile = ProfileHandler.getProfile(player)
                if (profile != null) {
                    try {
                        tickCosmetics(player, profile)
                        tickTrack(player, profile)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            try {
                sleep(50L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun tickCosmetics(player: Player, profile: Profile) {
        for (cosmetic in profile.getEnabledCosmetics()) {
            if (cosmetic is TickableCosmetic) {
                cosmetic.tick(player)
            }
        }
    }

    private fun tickTrack(player: Player, profile: Profile) {
        if (profile.track != null) {
            try {
                profile.track!!.tick()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}