package net.evilblock.cosmetics.category.hidden.emote

import org.bukkit.Location
import org.bukkit.inventory.ItemStack

interface Emote {

    fun getDisplayName(): String

    fun getDescription(): List<String>

    fun getIcon(): ItemStack

    fun playEffect(location: Location)

}