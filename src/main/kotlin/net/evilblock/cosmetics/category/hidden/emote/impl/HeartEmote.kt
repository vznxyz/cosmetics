package net.evilblock.cosmetics.category.hidden.emote.impl

import net.evilblock.cosmetics.util.particle.ParticleUtil
import net.evilblock.cosmetics.category.hidden.emote.Emote
import net.evilblock.cosmetics.util.particle.ParticleMeta
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class HeartEmote : Emote {

    override fun getDisplayName(): String {
        return "Heart"
    }

    override fun getDescription(): List<String> {
        return arrayListOf("${ChatColor.GOLD}Perform a ${ChatColor.RED}❤ ${ChatColor.GOLD}emote.")
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.RED_ROSE)
    }

    override fun playEffect(location: Location) {
        ParticleUtil.sendsParticleToAll(
            ParticleMeta(
                location,
                EnumParticle.HEART
            )
        )
    }

}