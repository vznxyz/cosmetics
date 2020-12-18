package net.evilblock.cosmetics.category.emote

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.EmotesCosmeticCategory
import net.evilblock.cosmetics.category.emote.impl.HeartEmote
import net.evilblock.cosmetics.menu.EmoteBoxMenu
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

object EmotesBoxCosmetic : Cosmetic() {

    val emotes = arrayListOf<Emote>(HeartEmote())

    override fun getCategory(): CosmeticCategory {
        return EmotesCosmeticCategory
    }

    override fun getID(): String {
        return "emotes-box"
    }

    override fun getName(): String {
        return "Emotes Box"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.CHEST)
    }

    override fun getPermission(): String {
        return "cosmetics.emote-box"
    }

    override fun hasState(): Boolean {
        return false
    }

    override fun onEnable(player: Player) {

    }

    @EventHandler
    fun onPlayerInteractEvent(event: PlayerInteractEvent) {
        if (event.action == Action.RIGHT_CLICK_AIR || event.action == Action.RIGHT_CLICK_BLOCK) {
            val itemInHand = event.player.itemInHand
            if (itemInHand != null) {
                if (itemInHand == EMOTE_BOX) {
                    EmoteBoxMenu().openMenu(event.player)
                }

                event.isCancelled = true
            }
        }
    }

    @JvmStatic
    val EMOTE_BOX = ItemBuilder.of(Material.CHEST)
            .name("${ChatColor.GREEN}${ChatColor.BOLD}Emote Box")
            .build()

}