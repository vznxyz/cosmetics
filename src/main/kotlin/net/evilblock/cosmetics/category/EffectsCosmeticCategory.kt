package net.evilblock.cosmetics.category

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.effect.BubblesEffect
import net.evilblock.cosmetics.category.effect.trail.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object EffectsCosmeticCategory : CosmeticCategory {

    override fun getName(): String {
        return "Effects"
    }

    override fun getPluralName(): String {
        return "effects"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.BLAZE_POWDER)
    }

    override fun getCosmetics(): List<Cosmetic> {
        return listOf(
                BubblesEffect,
                LavaRingsEffect,
                FlameRingsEffect,
                WaterRingsEffect,
                SnowRingsEffect
        )
    }

}