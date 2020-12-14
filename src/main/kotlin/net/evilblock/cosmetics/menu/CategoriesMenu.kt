package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.category.*
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.BackButton
import net.evilblock.cubed.menu.buttons.GlassButton
import net.evilblock.cubed.util.bukkit.enchantment.GlowEnchantment
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class CategoriesMenu : Menu() {

    init {
        updateAfterClick = true
    }

    override fun getTitle(player: Player): String {
        return "${ChatColor.BLUE}${ChatColor.BOLD}Cosmetics"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            for (i in 0 until 9) {
                buttons[i] = GlassButton(0)
            }

            buttons[0] = BackButton {  }
            buttons[4] = InfoButton()

            buttons[20] = CategoryButton(ArmorCosmeticCategory)
            buttons[21] = CategoryButton(CostumesCosmeticCategory)

            buttons[23] = CategoryButton(TracksCosmeticCategory)
            buttons[24] = CategoryButton(EffectsCosmeticCategory)
        }
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 36
    }

    private inner class InfoButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.BLUE}${ChatColor.BOLD}Cosmetics"
        }

        override fun getMaterial(player: Player): Material {
            return Material.TRIPWIRE_HOOK
        }

        override fun getButtonItem(player: Player): ItemStack {
            return super.getButtonItem(player).also { GlowEnchantment.addGlow(it) }
        }
    }

}