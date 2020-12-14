package net.evilblock.cosmetics.category.effect.trail

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.EffectsCosmeticCategory
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object FlameRingsEffect : RingsEffect(EnumParticle.FLAME) {

    override fun getCategory(): CosmeticCategory {
        return EffectsCosmeticCategory
    }

    override fun getID(): String {
        return "flame-rings"
    }

    override fun getName(): String {
        return "Flame Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of flames."
    }

    override fun getPermission(): String {
        return "cosmetics.flame-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.FIREBALL)
    }

}