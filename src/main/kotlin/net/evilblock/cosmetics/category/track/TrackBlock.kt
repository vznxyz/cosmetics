package net.evilblock.cosmetics.category.track

import net.evilblock.cubed.util.bukkit.ColorUtil
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

data class TrackBlock(
        val blockData: MaterialData,
        var chance: Double = 0.0
) {

    fun blockName(): String {
        if (blockData.itemType == Material.WOOL) {
            return ColorUtil.getWoolName(blockData.data) + " Wool"
        }

        val itemName = ItemUtils.getName(ItemStack(blockData.itemType, 1, blockData.data.toShort()))
        return if (blockData.data != 0.toByte()) {
            "$itemName (${blockData.data})"
        } else {
            itemName
        }
    }

}