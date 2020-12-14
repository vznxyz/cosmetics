package net.evilblock.cosmetics.category

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.hidden.EmotesBoxCosmetic
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object HiddenCosmeticCategory : CosmeticCategory {

    private val cosmetics = arrayListOf<Cosmetic>(
            EmotesBoxCosmetic
    )

    override fun getIcon(): ItemStack {
        return ItemStack(Material.AIR)
    }

    override fun getName(): String {
        return "Hidden"
    }

    override fun getPluralName(): String {
        return "hidden"
    }

    override fun getCosmetics(): List<Cosmetic> {
        return cosmetics
    }

}