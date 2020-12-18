package net.evilblock.cosmetics.util.particle

import net.evilblock.cosmetics.CosmeticsPlugin
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer

/**
 * A class to simplify sending particles to players.
 *
 * Also created to fix the issue of bukkit sometimes
 * not sending the particle or sending the particle
 * in varying speeds / sizes.
 */
object ParticleUtil {

    // name x y z offX offY offZ speed count
    fun sendsParticleToAll(world: World, vararg particleMetas: ParticleMeta) {
        val packets = arrayListOf<Pair<Location, PacketPlayOutWorldParticles>>()

        for (meta in particleMetas) {
            packets.add(meta.location to PacketPlayOutWorldParticles(
                    meta.particle,
                    true,
                    meta.location.x.toFloat(),
                    meta.location.y.toFloat(),
                    meta.location.z.toFloat(),
                    meta.offsetX,
                    meta.offsetY,
                    meta.offsetZ,
                    meta.speed,
                    meta.amount
            ))
        }

        for (player in world.players) {
            if (!CosmeticsPlugin.instance.hook.canRenderEffects(player)) {
                continue
            }

            for (packet in packets) {
                if (player.location.distance(packet.first) > 64.0) {
                    continue
                }

                (player as CraftPlayer).handle.playerConnection.sendPacket(packet.second)
            }
        }
    }

}