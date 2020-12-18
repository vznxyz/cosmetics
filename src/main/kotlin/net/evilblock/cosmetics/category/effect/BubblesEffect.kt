package net.evilblock.cosmetics.category.effect

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.category.EffectsCosmeticCategory
import net.evilblock.cosmetics.util.particle.ParticleMeta
import net.evilblock.cosmetics.util.particle.ParticleUtil
import net.minecraft.server.v1_12_R1.EnumParticle
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ThreadLocalRandom

object BubblesEffect : TickableCosmetic(), Listener {

    override fun getCategory(): CosmeticCategory {
        return EffectsCosmeticCategory
    }

    override fun getID(): String {
        return "bubbles"
    }

    override fun getName(): String {
        return "Bubbles"
    }

    override fun getDescription(): String {
        return "Surround yourself with bubbles."
    }

    override fun getPermission(): String {
        return "cosmetics.bubbles"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.CLAY_BALL)
    }

    override fun tick(player: Player) {
        var ticks = ticks.putIfAbsent(player.uniqueId, 0) ?: 0
        ticks++

        val location = player.location.clone()
        location.x = ThreadLocalRandom.current().nextDouble(location.x - 0.33, location.x + 0.33)
        location.y = ThreadLocalRandom.current().nextDouble(location.y, player.eyeLocation.y)
        location.z = ThreadLocalRandom.current().nextDouble(location.z - 0.33, location.z + 0.33)

        ParticleUtil.sendsParticleToAll(location.world, ParticleMeta(location, EnumParticle.SPELL_MOB_AMBIENT, 0F, 0F, 0F, 1F, 10))

        if (ticks >= 6) {
            ticks = 0
        }

        this.ticks[player.uniqueId] = ticks
    }

}