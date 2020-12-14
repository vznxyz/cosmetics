package net.evilblock.cosmetics.category.effect.trail

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.EffectsCosmeticCategory
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object LavaRingsEffect : RingsEffect(EnumParticle.DRIP_LAVA) {

    override fun getCategory(): CosmeticCategory {
        return EffectsCosmeticCategory
    }

    override fun getID(): String {
        return "lava-rings"
    }

    override fun getName(): String {
        return "Lava Rings"
    }

    override fun getDescription(): String {
        return "Surround yourself with rings made of lava."
    }

    override fun getPermission(): String {
        return "cosmetics.lava-rings"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.LAVA_BUCKET)
    }

}