package net.evilblock.cosmetics.category

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.emote.EmotesBoxCosmetic
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object EmotesCosmeticCategory : CosmeticCategory {

    private val cosmetics = arrayListOf<Cosmetic>(
            EmotesBoxCosmetic
    )

    override fun getIcon(): ItemStack {
        return ItemStack(Material.CHEST)
    }

    override fun getName(): String {
        return "Emotes"
    }

    override fun getPluralName(): String {
        return "emotes"
    }

    override fun getCosmetics(): List<Cosmetic> {
        return cosmetics
    }

}