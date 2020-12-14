package net.evilblock.cosmetics.event

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cubed.plugin.PluginEvent
import org.bukkit.entity.Player

class PlayerToggleCosmeticEvent(val player: Player, val cosmetic: Cosmetic, val enabled: Boolean) : PluginEvent()