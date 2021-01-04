package net.evilblock.cosmetics.thread

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.profile.Profile
import net.evilblock.cosmetics.profile.ProfileHandler
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class CosmeticsThread : Thread("Cosmetics - Tick Thread") {

    var lastSave: Long = System.currentTimeMillis()

    override fun run() {
        while (true) {
            try {
                runLogic()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                sleep(50L)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun runLogic() {
        if (System.currentTimeMillis() - lastSave >= 30_000L) {
            lastSave = System.currentTimeMillis()

            for (profile in ProfileHandler.getProfiles()) {
                try {
                    if (profile.requiresSave) {
                        ProfileHandler.saveProfile(profile)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        for (player in Bukkit.getOnlinePlayers()) {
            try {
                if (!player.isOnline) {
                    continue
                }

                val profile = ProfileHandler.getProfile(player)
                if (profile != null) {
                    if (!CosmeticsPlugin.instance.hook.inSupportedRegion(player)) {
                        for (cosmetic in profile.getEnabledCosmetics()) {
                            ProfileHandler.toggleCosmetic(player, cosmetic)
                        }

                        profile.track?.clearChanges()
                        profile.track = null

                        continue
                    }

                    tickCosmetics(player, profile)
                    tickTrack(player, profile)
                }
            } catch (e: Exception) {
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