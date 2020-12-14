package net.evilblock.cosmetics.category.armor

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.TickableCosmetic
import net.evilblock.cosmetics.category.ArmorCosmeticCategory
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.*

class RainbowArmorCosmetic : TickableCosmetic(), Listener {

    private val map = hashMapOf<UUID, RainbowData>()
    private val name = ChatColor.translateAlternateColorCodes('&', "&4&lR&c&lA&6&lI&e&lN&a&lB&2&lO&b&lW &3&lA&9&lR&1&lM&5&lO&d&lR")

    override fun getCategory(): CosmeticCategory {
        return ArmorCosmeticCategory
    }

    override fun getID(): String {
        return "rainbow-armor"
    }

    override fun getName(): String {
        return name
    }

    override fun getDescription(): String {
        return "A set of armor that cycles through the color palette."
    }

    override fun getIcon(): ItemStack {
        return ItemBuilder.of(Material.WOOL).data(14).build()
    }

    override fun getPermission(): String {
        return "cosmetics.rainbow-armor"
    }

    override fun canEnable(player: Player): Boolean {
        if (player.inventory.helmet != null
                || player.inventory.chestplate != null
                || player.inventory.leggings != null
                || player.inventory.boots != null) {
            player.sendMessage("${ChatColor.RED}You must unequip all of your armor to enable this cosmetic!")
        }

        return true
    }

    override fun onEnable(player: Player) {
        val data = RainbowData()
        map[player.uniqueId] = data

        player.inventory.helmet = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_HELMET).name(name).build(), data.color)
        player.inventory.chestplate = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_CHESTPLATE).name(name).build(), data.color)
        player.inventory.leggings = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_LEGGINGS).name(name).build(), data.color)
        player.inventory.boots = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_BOOTS).name(name).build(), data.color)
        player.updateInventory()
    }

    override fun onDisable(player: Player) {
        player.inventory.helmet = null
        player.inventory.chestplate = null
        player.inventory.leggings = null
        player.inventory.boots = null
        player.updateInventory()
    }

    override fun tick(player: Player) {
        val data = map[player.uniqueId] ?: RainbowData()

        data.colorHsl += 0.02

        if (data.colorHsl > 1) {
            data.colorHsl = 0.0
        }

        data.color = hue2rgb(data.colorHsl)

        if (System.currentTimeMillis() >= data.nextBroadcast) {
            data.nextBroadcast = System.currentTimeMillis() + 200L

            player.inventory.helmet = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_HELMET).name(name).build(), data.color)
            player.inventory.chestplate = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_CHESTPLATE).name(name).build(), data.color)
            player.inventory.leggings = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_LEGGINGS).name(name).build(), data.color)
            player.inventory.boots = colorLeatherArmor(ItemBuilder.of(Material.LEATHER_BOOTS).name(name).build(), data.color)

//            MinecraftProtocol.send(
//                player,
//                MinecraftProtocol.buildEntityEquipmentPacket(player.entityId, MinecraftReflection.ENUM_ITEM_SLOT_HEAD, player.inventory.helmet),
//                MinecraftProtocol.buildEntityEquipmentPacket(player.entityId, MinecraftReflection.ENUM_ITEM_SLOT_CHEST, player.inventory.chestplate),
//                MinecraftProtocol.buildEntityEquipmentPacket(player.entityId, MinecraftReflection.ENUM_ITEM_SLOT_LEGS, player.inventory.leggings),
//                MinecraftProtocol.buildEntityEquipmentPacket(player.entityId, MinecraftReflection.ENUM_ITEM_SLOT_FEET, player.inventory.boots)
//            )
        }

        if (!map.containsKey(player.uniqueId)) {
            map[player.uniqueId] = data
        }
    }

    data class RainbowData(var colorHsl: Double = 0.0, var color: Color = hue2rgb(0.0), var nextBroadcast: Long = System.currentTimeMillis() + 200L)

    companion object {
        /**
         * Applies the given [color] to the given [itemStack]'s metadata.
         */
        private fun colorLeatherArmor(itemStack: ItemStack, color: Color): ItemStack {
            val meta = itemStack.itemMeta as LeatherArmorMeta
            meta.color = color
            itemStack.itemMeta = meta
            return itemStack
        }

        private fun hue2rgb(h: Double): Color {
            var r = 0.0
            var g = 0.0
            var b = 0.0
            val i = (h * 6).toInt()
            val f = h * 6 - i
            val q = 1 - f
            when (i % 6) {
                0 -> {
                    r = 1.0
                    g = f
                }
                1 -> {
                    r = q
                    g = 1.0
                }
                2 -> {
                    g = 1.0
                    b = f
                }
                3 -> {
                    g = q
                    b = 1.0
                }
                4 -> {
                    r = f
                    b = 1.0
                }
                5 -> {
                    r = 1.0
                    b = q
                }
            }
            return Color.fromRGB((r * 255).toInt(), (g * 255).toInt(), (b * 255).toInt())
        }
    }

}