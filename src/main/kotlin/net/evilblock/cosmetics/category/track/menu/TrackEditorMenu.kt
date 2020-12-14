package net.evilblock.cosmetics.category.track.menu

import net.evilblock.cosmetics.category.track.Track
import net.evilblock.cosmetics.category.track.TrackHandler
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.buttons.AddButton
import net.evilblock.cubed.menu.buttons.GlassButton
import net.evilblock.cubed.menu.menus.ConfirmMenu
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.NumberUtils
import net.evilblock.cubed.util.TextSplitter
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.bukkit.prompt.EzPrompt
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView

class TrackEditorMenu : PaginatedMenu() {

    init {
        updateAfterClick = true
    }

    override fun getPrePaginatedTitle(player: Player): String {
        return "Track Editor"
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button> {
        val buttons = hashMapOf<Int, Button>()

        for (track in TrackHandler.getTracks()) {
            buttons[buttons.size] = TrackButton(track)
        }

        return buttons
    }

    override fun getGlobalButtons(player: Player): Map<Int, Button>? {
        val buttons = hashMapOf<Int, Button>()

        buttons[2] = AddTrackButton()

        for (i in 9..17) {
            buttons[i] = GlassButton(0)
        }

        return buttons
    }

    override fun getPageButtonSlots(): Pair<Int, Int> {
        return Pair(0, 8)
    }

    override fun getButtonsStartOffset(): Int {
        return 9
    }

    override fun getMaxItemsPerPage(player: Player): Int {
        return 36
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 54
    }

    private inner class TrackButton(private val track: Track) : Button() {
        override fun getName(player: Player): String {
            return track.getName()
        }

        override fun getDescription(player: Player): List<String> {
            val description = arrayListOf<String>()

            description.add("${ChatColor.GRAY}(ID: ${track.getRawID()})")
            description.add("${ChatColor.GRAY}(${track.getPermission()})")
            description.add("")

            if (track.blockTypes.isEmpty()) {
                description.add("${ChatColor.GRAY}- Has no blocks")
            } else {
                for (blockType in track.blockTypes) {
                    description.add("${ChatColor.GRAY}- ${blockType.blockName()} ${ChatColor.GRAY}(${NumberUtils.formatDecimal(blockType.chance)}%)")
                }
            }

            description.add("")
            description.add("${ChatColor.GREEN}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.GREEN}to edit track")
            description.add("${ChatColor.RED}${ChatColor.BOLD}RIGHT-CLICK ${ChatColor.RED}to edit track")

            return description
        }

        override fun getMaterial(player: Player): Material {
            return Material.RAILS
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            if (clickType.isLeftClick) {
                EditTrackMenu(track).openMenu(player)
            } else if (clickType.isRightClick) {
                ConfirmMenu { confirmed ->
                    if (confirmed) {
                        TrackHandler.forgetTrack(track)

                        Tasks.async {
                            TrackHandler.saveData()
                        }
                    }
                }.openMenu(player)
            }
        }
    }

    private inner class AddTrackButton : AddButton() {
        override fun getName(player: Player): String {
            return "${ChatColor.AQUA}${ChatColor.BOLD}Add New Track"
        }

        override fun getDescription(player: Player): List<String> {
            val description = arrayListOf<String>()

            description.add("")
            description.addAll(TextSplitter.split(text = "Add a new track by completing the setup procedure.", linePrefix = ChatColor.GRAY.toString()))
            description.add("")
            description.add("${ChatColor.GREEN}${ChatColor.BOLD}LEFT-CLICK ${ChatColor.GREEN}to add new track")

            return description
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            if (clickType.isLeftClick) {
                EzPrompt.Builder()
                    .charLimit(48)
                    .promptText("${ChatColor.GREEN}Please input a unique ID for the new track.")
                    .acceptInput { _, input ->
                        val track = Track(id = input)
                        TrackHandler.trackTrack(track)

                        Tasks.async {
                            TrackHandler.saveData()
                        }

                        EditTrackMenu(track).openMenu(player)
                    }
                    .build()
                    .start(player)
            }
        }
    }

}