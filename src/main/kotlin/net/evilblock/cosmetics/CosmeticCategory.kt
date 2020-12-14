package net.evilblock.cosmetics

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

interface CosmeticCategory {

    fun getIcon(): ItemStack

    fun getName(): String

    fun getPluralName(): String

    fun getCosmetics(): Collection<Cosmetic>

    fun getAccessibleCosmetics(player: Player): List<Cosmetic> {
        return getCosmetics()
                .filter { cosmetic -> player.hasPermission(cosmetic.getPermission()) }
                .toList()
    }

}