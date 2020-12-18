package net.evilblock.cosmetics.category.track.player

import net.evilblock.cosmetics.CosmeticsPlugin
import net.evilblock.cosmetics.category.track.Track
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class PlayerTrack(val track: Track) {

    var cachedPlayer: Player? = null

    private var processTicks: Int = 0

    private val queuedChanges: MutableSet<Location> = ConcurrentHashMap.newKeySet()
    private val completedChanges: MutableMap<Location, AtomicLong> = hashMapOf()

    private val watchers: MutableList<Player> = arrayListOf()

    fun addBlockChange(location: Location) {
        queuedChanges.add(location)
    }

    fun tick() {
        if (cachedPlayer == null) {
            println("cached player null")
            return
        }

        processTicks++

        if (processTicks >= 10) {
            tickWatchers()
        }

        tickBlockUpdates()

        if (processTicks >= 10) {
            tickExpirations()
            processTicks = 0
        }
    }

    private fun tickWatchers() {
        for (player in Bukkit.getOnlinePlayers()) {
            // respect user settings
            if (!CosmeticsPlugin.instance.hook.canRenderTracks(player)) {
                if (watchers.contains(player)) {
                    clearChanges(player)
                    watchers.remove(player)
                }

                continue
            }

            // distance tells us if the track is visible to the player
            val distance = if (player.location.world == cachedPlayer!!.location.world) {
                player.location.distance(cachedPlayer!!.location)
            } else {
                -1.0
            }

            if (watchers.contains(player)) {
                if (distance < 0.0 || distance > 64) {
                    clearChanges(player)
                    watchers.remove(player)
                }
            } else {
                if (distance >= 0.0 && distance < 64) {
                    watchers.add(player)
                }
            }
        }
    }

    private fun tickBlockUpdates() {
        val queueIterator = queuedChanges.iterator()
        while (queueIterator.hasNext()) {
            val location = queueIterator.next()
            queueIterator.remove()

            val expiry = if (completedChanges.containsKey(location)) {
                completedChanges[location]!!.get()
            } else {
                -1L
            }

            completedChanges[location] = AtomicLong(System.currentTimeMillis() + track.blockTime)

            if (expiry == -1L || System.currentTimeMillis() >= expiry) {
                val nextBlockType = track.nextBlockType()

                for (watcher in watchers) {
                    watcher.sendBlockChange(location, nextBlockType.blockData.itemType, nextBlockType.blockData.data)
                }
            }
        }
    }

    private fun tickExpirations() {
        val clearedChanges = arrayListOf<Location>()
        for ((location, expiration) in completedChanges) {
            if (System.currentTimeMillis() >= expiration.get()) {
                if (location.world == cachedPlayer!!.location.world) {
                    if (location.distance(cachedPlayer!!.location) >= 3) {
                        clearedChanges.add(location)
                    } else {
                        expiration.set(System.currentTimeMillis() + track.blockTime)
                    }
                }
            }
        }

        for (change in clearedChanges) {
            completedChanges.remove(change)
        }

        if (watchers.isNotEmpty() && clearedChanges.isNotEmpty()) {
            for (player in watchers) {
                for (location in clearedChanges) {
                    if (!location.isChunkLoaded) {
                        continue
                    }

                    val block = location.block
                    player.sendBlockChange(location, block.type, block.data)
                }
            }
        }
    }

    fun clearChanges() {
        if (watchers.isNotEmpty()) {
            for (player in watchers) {
                for (location in completedChanges.keys) {
                    if (!location.isChunkLoaded) {
                        continue
                    }

                    val block = location.block
                    player.sendBlockChange(location, block.type, block.data)
                }
            }
        }
    }

    fun clearChanges(player: Player) {
        for (location in completedChanges.keys) {
            if (!location.isChunkLoaded) {
                continue
            }

            val block = location.block
            player.sendBlockChange(location, block.type, block.data)
        }
    }

}