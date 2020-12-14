package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.category.hidden.emote.Emote
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class EmoteButton(private val emote: Emote) : Button() {

    override fun getButtonItem(player: Player): ItemStack {
        val lore = arrayListOf<String>().also {
            it.add("")

            for (line in emote.getDescription()) {
                it.add(line)
            }

            it.add("")
            it.add("${ChatColor.YELLOW}Click to perform this emote")
        }

        return ItemBuilder.copyOf(emote.getIcon())
                .name("${ChatColor.YELLOW}${emote.getDisplayName()}")
                .setLore(lore)
                .addFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON)
                .build()
    }

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
        player.closeInventory()
        emote.playEffect(player.eyeLocation.clone().add(player.location.direction))
    }

}