package net.evilblock.cosmetics.menu

import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.Material
import org.bukkit.entity.Player
import kotlin.math.ceil

class CosmeticsMenu(private val category: CosmeticCategory) : Menu() {

    init {
        placeholder = true
        updateAfterClick = true
    }

    override fun getTitle(player: Player): String {
        return category.getName()
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->
            var slotIndex = 0

            category.getCosmetics().forEach { cosmetic ->
                if (cosmetic.hasState()) {
                    buttons[slots[slotIndex++]] = CosmeticButton(cosmetic)
                }
            }

            slotIndex--

            // fill remaining row slots
            for (i in 1 .. 8) {
                if (!slots.contains(slots[slotIndex] + i)) {
                    break
                }

                buttons[slots[slotIndex] + i] = Button.placeholder(Material.AIR)
            }

            // make bottom glass border row
            buttons[getSlot(9, ceil((slotIndex + 2) / 9.0).toInt())] = Button.placeholder(Material.STAINED_GLASS_PANE, 15, " ")
        }
    }

    override fun onClose(player: Player, manualClose: Boolean) {
        if (manualClose) {
            Tasks.delayed(1L) {
                CategoriesMenu().openMenu(player)
            }
        }
    }

    companion object {
        private val slots = arrayListOf<Int>()

        init {
            slots.addAll(10 .. 16)
            slots.addAll(19 .. 25)
            slots.addAll(28 .. 34)
            slots.addAll(37 .. 43)
        }
    }

}