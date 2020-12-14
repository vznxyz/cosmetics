package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.category.armor.RankArmorCosmetic
import net.evilblock.cosmetics.profile.ProfileHandler
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.TextSplitter
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta

class CosmeticButton(private val cosmetic: Cosmetic) : Button() {

    override fun getMaterial(player: Player): Material {
        return cosmetic.getIcon().type
    }

    override fun getDamageValue(player: Player): Byte {
        return cosmetic.getIcon().data.data
    }

    override fun applyMetadata(player: Player, itemMeta: ItemMeta): ItemMeta? {
        if (cosmetic is RankArmorCosmetic) {
            (itemMeta as LeatherArmorMeta).color = cosmetic.color
        }

        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)

        return itemMeta
    }

    override fun getName(player: Player): String {
        return if (player.hasPermission(cosmetic.getPermission())) {
            val color = if (ProfileHandler.isCosmeticEnabled(player, cosmetic)) {
                ChatColor.GREEN.toString()
            } else {
                ChatColor.RED.toString()
            }

            color + ChatColor.BOLD + cosmetic.getName()
        } else {
            "${ChatColor.RED}${ChatColor.BOLD}" + cosmetic.getName()
        }
    }

    override fun getDescription(player: Player): List<String> {
        return arrayListOf<String>().also { desc ->
            if (player.isOp || player.hasPermission("cosmetics.admin")) {
                desc.add("${ChatColor.GRAY}(${cosmetic.getPermission()})")
            }

            desc.add("")

            if (player.hasPermission(cosmetic.getPermission())) {
                desc.addAll(TextSplitter.split(linePrefix = ChatColor.GRAY.toString(), text = cosmetic.getDescription()))
                desc.add("")

                val profile = ProfileHandler.getProfile(player)
                if (profile != null) {
                    if (profile.isCosmeticEnabled(cosmetic)) {
                        desc.add(styleAction(ChatColor.RED, "LEFT-CLICK", "to disable this cosmetic"))
                    } else {
                        desc.add(styleAction(ChatColor.GREEN, "LEFT-CLICK", "to enable this cosmetic"))
                    }
                }
            } else {
                desc.add("${ChatColor.RED}${ChatColor.BOLD}COSMETIC LOCKED")
                desc.add("${ChatColor.RED}You don't have access to")
                desc.add("${ChatColor.RED}this cosmetic. To get access")
                desc.add("${ChatColor.RED}purchase a rank on our store.")
            }
        }
    }

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
        if (player.hasPermission(cosmetic.getPermission())) {
            ProfileHandler.toggleCosmetic(player, cosmetic)
        }
    }

}