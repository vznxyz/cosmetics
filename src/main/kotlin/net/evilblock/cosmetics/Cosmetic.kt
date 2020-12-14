package net.evilblock.cosmetics

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class Cosmetic {

    abstract fun getCategory(): CosmeticCategory

    abstract fun getID(): String

    abstract fun getName(): String

    open fun getDescription(): String {
        return ""
    }

    abstract fun getPermission(): String

    open fun hiddenIfNotPermitted(): Boolean {
        return false
    }

    abstract fun getIcon(): ItemStack

    open fun hasState(): Boolean {
        return true
    }

    open fun canEnable(player: Player): Boolean {
        return true
    }

    open fun onEnable(player: Player) {}

    open fun onDisable(player: Player) {}

}