package net.evilblock.cosmetics.category.effect.trail

import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.util.particle.ParticleMeta
import net.evilblock.cosmetics.util.particle.ParticleUtil
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import kotlin.math.cos
import kotlin.math.sin

abstract class RingsEffect(private val particle: EnumParticle) : TickableCosmetic(), Listener {

    override fun tick(player: Player) {
        var ticks = ticks.putIfAbsent(player.uniqueId, 0) ?: 0
        ticks++

        buildSmallRing(player, ticks, 0.5)
        buildBigRing(player, ticks, 0.9)
        buildSmallRing(player, ticks, 1.3)

        if (ticks >= 32) {
            ticks = 0
        }

        this.ticks[player.uniqueId] = ticks
    }

    private fun buildSmallRing(player: Player, ticks: Int, y: Double) {
        val angle = ticks * ((2 * Math.PI) / 32)
        val location = player.location.clone().add(0.6 * cos(angle), y, 0.6 * sin(angle))
        ParticleUtil.sendsParticleToAll(ParticleMeta(location, particle, 0F, 0F, 0F, 0F, 2))
    }

    private fun buildBigRing(player: Player, ticks: Int, y: Double) {
        val angle = ticks * ((2 * Math.PI) / 32)
        val location = player.location.clone().add(0.8 * cos(angle), y, 0.8 * sin(angle))
        ParticleUtil.sendsParticleToAll(ParticleMeta(location, particle, 0F, 0F, 0F, 0F, 2))
    }

}