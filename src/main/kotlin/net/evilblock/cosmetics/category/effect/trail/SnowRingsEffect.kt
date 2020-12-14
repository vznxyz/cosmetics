package net.evilblock.cosmetics.category.effect.trail

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.EffectsCosmeticCategory
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object SnowRingsEffect : RingsEffect(EnumParticle.SNOW_SHOVEL) {

    override fun getCategory(): CosmeticCategory {
        return EffectsCosmeticCategory
    }

    override fun getID(): String {
        return "snow-rings"
    }

    override fun getName(): String {
        return "Snow Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of snow."
    }

    override fun getPermission(): String {
        return "cosmetics.snow-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.SNOW_BALL)
    }

}