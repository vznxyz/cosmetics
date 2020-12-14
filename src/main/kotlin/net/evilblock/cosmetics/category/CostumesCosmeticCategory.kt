package net.evilblock.cosmetics.category

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object CostumesCosmeticCategory : CosmeticCategory {

    private val cosmetics = arrayListOf<Cosmetic>(

    )

    override fun getName(): String {
        return "Costumes"
    }

    override fun getPluralName(): String {
        return "costumes"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.JACK_O_LANTERN)
    }

    override fun getCosmetics(): Collection<Cosmetic> {
        return cosmetics
    }

}