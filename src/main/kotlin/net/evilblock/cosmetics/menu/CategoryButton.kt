package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class CategoryButton(private val category: CosmeticCategory) : Button() {

    override fun getButtonItem(player: Player): ItemStack {
        val lore = arrayListOf<String>().also { lore ->
            val unlocked = category.getCosmetics().count { player.hasPermission(it.getPermission()) }
            val total = category.getCosmetics().size

            lore.add("${ChatColor.YELLOW}Unlocked${ChatColor.RESET}: ${ChatColor.AQUA}$unlocked/$total")
            lore.add("")
            lore.add(styleAction(ChatColor.GREEN, "LEFT-CLICK", "to browse all ${category.getPluralName()}"))
        }

        return ItemBuilder.copyOf(category.getIcon())
                .name("${ChatColor.YELLOW}${ChatColor.BOLD}${category.getName()}")
                .setLore(lore)
                .addFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS)
                .build()
    }

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
        CosmeticsMenu(category).openMenu(player)
    }

}