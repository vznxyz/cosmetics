package net.evilblock.cosmetics.category.effect.trail

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.EffectsCosmeticCategory
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object WaterRingsEffect : RingsEffect(EnumParticle.DRIP_WATER) {

    override fun getCategory(): CosmeticCategory {
        return EffectsCosmeticCategory
    }

    override fun getID(): String {
        return "water-rings"
    }

    override fun getName(): String {
        return "Water Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of water."
    }

    override fun getPermission(): String {
        return "cosmetics.water-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.WATER_BUCKET)
    }

}