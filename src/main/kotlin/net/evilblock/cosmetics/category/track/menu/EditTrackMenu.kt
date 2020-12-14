package net.evilblock.cosmetics.category.track.menu

import net.evilblock.cosmetics.category.track.Track
import net.evilblock.cosmetics.category.track.TrackBlock
import net.evilblock.cosmetics.category.track.TrackHandler
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.menu.buttons.GlassButton
import net.evilblock.cubed.menu.menus.ConfirmMenu
import net.evilblock.cubed.menu.menus.SelectItemStackMenu
import net.evilblock.cubed.util.NumberUtils
import net.evilblock.cubed.util.TextSplitter
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.bukkit.prompt.EzPrompt
import net.evilblock.cubed.util.bukkit.prompt.NumberPrompt
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class EditTrackMenu(private val track: Track) : Menu() {

    init {
        updateAfterClick = true
    }

    override fun getTitle(player: Player): String {
        return "Edit Track - ${track.getName()}"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        buttons[0] = EditNameButton()
        buttons[2] = EditIconButton()

        for (i in 9..17) {
            buttons[i] = GlassButton(0)
        }

        for ((index, blockType) in track.blockTypes.sortedBy { it.chance }.withIndex()) {
            buttons[18 + index] = BlockTypeButton(blockType)
        }

        return buttons
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 54
    }

    override fun onClose(player: Player, manualClose: Boolean) {
        if (manualClose) {
            Tasks.delayed(1L) {
                TrackEditorMenu().openMenu(player)
            }
        }
    }

    override fun acceptsShiftClickedItem(player: Player, itemStack: ItemStack): Boolean {
        if (!itemStack.type.isBlock) {
            player.sendMessage("${ChatColor.RED}That item isn't an accepted block!")
            return false
        }

        track.blockTypes.add(TrackBlock(itemStack.data))

        Tasks.async {
            TrackHandler.saveData()
        }

        return true
    }

    private inner class BlockTypeButton(private val blockType: TrackBlock) : Button() {
        override fun getName(player: Player): String {
            return blockType.blockName()
        }

        override fun getDescription(player: Player): List<String> {
            val description = arrayListOf<String>()

            description.add("${ChatColor.GRAY}Chance: ${NumberUtils.formatDecimal(blockType.chance)}")
            description.add("")
            description.add("${ChatColor.GREEN}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.GREEN}to edit chance")

            return description
        }

        override fun getMaterial(player: Player): Material {
            return blockType.blockData.itemType
        }

        override fun getDamageValue(player: Player): Byte {
            return blockType.blockData.data
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            if (clickType.isLeftClick) {
                NumberPrompt()
                    .withText("${ChatColor.GREEN}Please input a new chance value. ${ChatColor.GRAY}(0.0 - 100.0)")
                    .acceptInput { number ->
                        if (number.toInt() < 0) {
                            player.sendMessage("${ChatColor.RED}Chance must be more than 0!")
                            return@acceptInput
                        }

                        if (number.toInt() > 100) {
                            player.sendMessage("${ChatColor.RED}Chance can't be more than 100!")
                            return@acceptInput
                        }

                        blockType.chance = number.toDouble()

                        Tasks.async {
                            TrackHandler.saveData()
                        }

                        this@EditTrackMenu.openMenu(player)
                    }.start(player)
            } else if (clickType.isRightClick) {
                ConfirmMenu { confirmed ->
                    if (confirmed) {
                        track.blockTypes.remove(blockType)

                        Tasks.async {
                            TrackHandler.saveData()
                        }
                    }

                    this@EditTrackMenu.openMenu(player)
                }.openMenu(player)
            }
        }
    }

    private inner class EditNameButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.AQUA}${ChatColor.BOLD}Edit Name"
        }

        override fun getDescription(player: Player): List<String> {
            val description = arrayListOf<String>()

            description.add("")
            description.addAll(TextSplitter.split(text = "The name is how you want the track to appear in chat and menu text.", linePrefix = ChatColor.GRAY.toString()))
            description.add("")
            description.add("${ChatColor.GREEN}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.GREEN}to edit name")

            return description
        }

        override fun getMaterial(player: Player): Material {
            return Material.NAME_TAG
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            if (clickType.isLeftClick) {
                EzPrompt.Builder()
                        .promptText("${ChatColor.GREEN}Please input a new name for the track.")
                        .acceptInput { _, input ->
                            track.displayName = ChatColor.translateAlternateColorCodes('&', input)

                            Tasks.async {
                                TrackHandler.saveData()
                            }

                            this@EditTrackMenu.openMenu(player)
                        }
                        .build()
                        .start(player)
            }
        }
    }

    private inner class EditIconButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.AQUA}${ChatColor.BOLD}Edit Icon"
        }

        override fun getDescription(player: Player): List<String> {
            val description = arrayListOf<String>()

            description.add("")
            description.addAll(TextSplitter.split(text = "The icon that is rendered to the user to represent this track.", linePrefix = ChatColor.GRAY.toString()))
            description.add("")
            description.add("${ChatColor.GREEN}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.GREEN}to edit icon")

            return description
        }

        override fun getMaterial(player: Player): Material {
            return Material.NETHER_STAR
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            if (clickType.isLeftClick) {
                SelectItemStackMenu() { selected ->
                    track.displayIcon = selected.clone()

                    Tasks.async {
                        TrackHandler.saveData()
                    }

                    this@EditTrackMenu.openMenu(player)
                }.openMenu(player)
            }
        }
    }

    private inner class EditDescriptionButton : Button() {

    }

    private inner class EditRadiusButton : Button() {

    }

    private inner class EditDurationButton : Button() {

    }

}