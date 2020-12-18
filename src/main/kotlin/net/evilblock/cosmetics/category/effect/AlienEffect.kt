package net.evilblock.cosmetics.category.effect

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.category.EffectsCosmeticCategory
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.inventory.ItemStack

object AlienEffect : TickableCosmetic(), Listener {

    override fun getCategory(): CosmeticCategory {
        return EffectsCosmeticCategory
    }

    override fun getID(): String {
        return "alien-rings"
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
        return ItemStack(Material.RECORD_12)
    }

    override fun tick(player: Player) {
//        var ticks = ticks.putIfAbsent(player.uniqueId, 0) ?: 0
//        ticks++
//
//        AlienRings.ticks[player.uniqueId] = ticks + 1
//
//        val location = player.location.clone().add(0.1, 0.0, 0.1)
//        val angle = ticks * ((2 * Math.PI) / 40)
//        val cos = cos(angle)
//        val sin = sin(angle)
//
//        val particleMetaList = arrayListOf<ParticleMeta>()
//
//        val xSlantedRingLocation = location.clone().add(1.1 * cos, 0.9 + (0.55 * cos), 1.1 * sin)
//        val zSlantedRingLocation = location.clone().add(1.1 * cos, 0.9 + (1.1 * sin), 1.1 * sin)
//
//        particleMetaList.add(ParticleMeta(verticalRingLocation, EnumParticle.COLOURED_DUST, 255.0F, 0.0F, 0.0F, 1.0F, 0))
//        particleMetaList.add(ParticleMeta(horizontalRingLocation, EnumParticle.COLOURED_DUST, 255.0F, 0.0F, 0.0F, 1.0F, 0))
//        particleMetaList.add(ParticleMeta(xSlantedRingLocation, EnumParticle.BLOCK_DUST, 255.0F, 0.0F, 0.0F, 1.0F, 0))
//        particleMetaList.add(ParticleMeta(zSlantedRingLocation, EnumParticle.COLOURED_DUST, 255.0F, 0.0F, 0.0F, 1.0F, 0))
//
//        ParticleUtil.sendsParticleToAll(*particleMetaList.toTypedArray())
//
//        if (ticks >= 40) {
//            ticks = 0
//        }
//
//        this.ticks[player.uniqueId] = ticks
    }

}