package net.evilblock.cosmetics.profile.listener

import net.evilblock.cosmetics.category.ArmorCosmeticCategory
import net.evilblock.cosmetics.category.CostumesCosmeticCategory
import net.evilblock.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType

object ProfileCosmeticListeners : Listener {

    /**
     * Prevents players with an enabled armor/costume cosmetic from taking off their armor.
     */
    @EventHandler
    fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.slotType == InventoryType.SlotType.ARMOR) {
            val profile = ProfileHandler.getProfile(event.whoClicked as Player)
            if (profile?.getEnabledCosmetics()?.any { it.getCategory() is ArmorCosmeticCategory || it.getCategory() is CostumesCosmeticCategory } == true) {
                event.isCancelled = true
            }
        }
    }

    /**
     * Prevents dropping armor/costume cosmetic items when a player dies.
     */
    @EventHandler
    fun onPlayerDeathEvent(event: PlayerDeathEvent) {
        val profile = ProfileHandler.getProfile(event.entity)
        if (profile?.getEnabledCosmetics()?.any { it.getCategory() is ArmorCosmeticCategory || it.getCategory() is CostumesCosmeticCategory } == true) {
            val iterator = event.drops.iterator()
            while (iterator.hasNext()) {
                val itemStack = iterator.next()
                if (ItemUtils.isArmorEquipment(itemStack.type)) {
                    iterator.remove()
                }
            }
        }
    }

}