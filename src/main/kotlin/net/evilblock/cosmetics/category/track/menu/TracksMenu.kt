package net.evilblock.cosmetics.category.track.menu

import net.evilblock.cosmetics.category.track.Track
import net.evilblock.cosmetics.category.track.TrackHandler
import net.evilblock.cosmetics.category.track.player.PlayerTrack
import net.evilblock.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.GlassButton
import net.evilblock.cubed.util.bukkit.enchantment.GlowEnchantment
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class TracksMenu : Menu() {
    init {
        updateAfterClick = true
    }

    override fun getTitle(player: Player): String {
        return "${ChatColor.DARK_AQUA}${ChatColor.BOLD}Tracks"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            for (i in 0 until 9) {
                buttons[i] = GlassButton(0)
            }

            buttons[4] = InfoButton()

            for ((index, track) in TrackHandler.getTracks().withIndex()) {
                buttons[9 + index] = TrackButton(track)
            }
        }
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 54
    }

    private inner class InfoButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.DARK_AQUA}${ChatColor.BOLD}Tracks"
        }

        override fun getMaterial(player: Player): Material {
            return Material.BOOK
        }

        override fun getButtonItem(player: Player): ItemStack {
            return super.getButtonItem(player).also {
                GlowEnchantment.addGlow(it)
            }
        }
    }

    private inner class TrackButton(private val track: Track) : Button() {
        override fun getName(player: Player): String {
            return track.getName()
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also { desc ->
                if (track.blockTypes.isEmpty()) {
                    desc.add("${ChatColor.GRAY}Has no blocks")
                } else {
                    for (blockType in track.blockTypes) {
                        desc.add("${ChatColor.GRAY}- ${blockType.blockName()}")
                    }
                }

                desc.add("")

                val playerTrack = ProfileHandler.getProfile(player)?.track

                when {
                    playerTrack != null && playerTrack.track == track -> {
                        desc.add("${ChatColor.RED}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.RED}to disable track")
                    }
                    player.hasPermission(track.getPermission()) -> {
                        desc.add("${ChatColor.GREEN}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.GREEN}to enable track")
                    }
                    else -> {
                        desc.add("${ChatColor.RED}${ChatColor.BOLD}TRACK LOCKED")
                    }
                }
            }
        }

        override fun getMaterial(player: Player): Material {
            return Material.STAINED_GLASS_PANE
        }

        override fun getDamageValue(player: Player): Byte {
            return if (player.hasPermission(track.getPermission())) {
                5
            } else {
                14
            }
        }

        override fun getButtonItem(player: Player): ItemStack {
            return super.getButtonItem(player).also {
                val playerTrack = ProfileHandler.getProfile(player)?.track
                if (playerTrack != null && playerTrack.track == track) {
                    GlowEnchantment.addGlow(it)
                }
            }
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            val profile = ProfileHandler.getProfile(player)
            if (profile == null) {
                player.sendMessage("${ChatColor.RED}Whoops! That shouldn't have happened...")
                player.sendMessage("${ChatColor.RED}(We couldn't fetch your cosmetics data)")
                return
            }

            if (!player.hasPermission(track.getPermission())) {
                player.sendMessage("${ChatColor.RED}You haven't unlocked that track!")
                return
            }

            if (clickType.isLeftClick) {
                profile.track?.clearChanges()

                if (profile.track != null && profile.track!!.track == track) {
                    profile.track = null
                } else {
                    profile.track = PlayerTrack(track)
                }
            }
        }
    }

}