package net.evilblock.cosmetics.category.track

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.TracksCosmeticCategory
import net.evilblock.cubed.util.Chance
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

class Track(private val id: String) : Cosmetic(), Listener {

    var displayName: String = id.toLowerCase()
    var displayIcon: ItemStack? = null
    var desc: String = ""

    var radius: Int = 5

    var blockTime: Long = 3000L
    val blockTypes: MutableList<TrackBlock> = arrayListOf()

    override fun getCategory(): CosmeticCategory {
        return TracksCosmeticCategory
    }

    override fun getID(): String {
        return "track-$id"
    }

    fun getRawID(): String {
        return id
    }

    override fun getName(): String {
        return "$displayName Track"
    }

    override fun getIcon(): ItemStack {
        return displayIcon ?: ItemStack(Material.RAILS)
    }

    override fun getDescription(): String {
        return desc
    }

    override fun getPermission(): String {
        return "cosmetics.tracks.${ChatColor.stripColor(displayName.toLowerCase())}"
    }

    fun nextBlockType(): TrackBlock {
        if (blockTypes.isEmpty()) {
            throw IllegalStateException("Track has no block types registered")
        }

        if (!blockTypes.any { it.chance > 0.0 }) {
            throw IllegalStateException("Track has no block types with a chance more than 0")
        }

        var tries = 5
        while (tries > 0) {
            for (blockType in blockTypes) {
                if (Chance.percent(blockType.chance)) {
                    return blockType
                }
            }
            tries--
        }

        return blockTypes.random()
    }

}