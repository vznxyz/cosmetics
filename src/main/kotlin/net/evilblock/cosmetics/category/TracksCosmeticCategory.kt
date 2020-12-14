package net.evilblock.cosmetics.category

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.track.Track
import net.evilblock.cosmetics.category.track.TrackHandler
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object TracksCosmeticCategory : CosmeticCategory {

    override fun getName(): String {
        return "Tracks"
    }

    override fun getPluralName(): String {
        return "tracks"
    }

    override fun getIcon(): ItemStack {
        return ItemStack(Material.RAILS)
    }

    override fun getCosmetics(): Collection<Track> {
        return TrackHandler.getTracks()
    }

}