package net.evilblock.cosmetics

import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class TickableCosmetic : Cosmetic() {

    internal val ticks: MutableMap<UUID, Int> = ConcurrentHashMap()

    open fun tick(player: Player) {}

}