package net.evilblock.cosmetics

import net.evilblock.cosmetics.category.*
import net.evilblock.cosmetics.category.track.TrackHandler
import net.evilblock.cosmetics.category.track.command.TracksCommand
import net.evilblock.cosmetics.category.track.command.TracksEditorCommand
import net.evilblock.cosmetics.profile.listener.ProfileTrackListeners
import net.evilblock.cosmetics.command.CosmeticsCommand
import net.evilblock.cosmetics.command.CosmeticsListCommand
import net.evilblock.cosmetics.command.ReloadCommand
import net.evilblock.cosmetics.command.WipeCommand
import net.evilblock.cosmetics.hook.CosmeticsHook
import net.evilblock.cosmetics.profile.listener.ProfileCosmeticListeners
import net.evilblock.cosmetics.menu.CategoriesMenu
import net.evilblock.cosmetics.profile.ProfileHandler
import net.evilblock.cosmetics.profile.listener.ProfileLoadListeners
import net.evilblock.cosmetics.thread.CosmeticsThread
import net.evilblock.cubed.command.CommandHandler
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class CosmeticsPlugin : JavaPlugin() {

    companion object {
        @JvmStatic
        lateinit var instance: CosmeticsPlugin
    }

    var hook: CosmeticsHook? = null

    val categories: List<CosmeticCategory> = arrayListOf(
            ArmorCosmeticCategory,
            CostumesCosmeticCategory,
            HiddenCosmeticCategory,
            TracksCosmeticCategory,
            EffectsCosmeticCategory
    )

    val cosmetics: MutableMap<String, Cosmetic> = hashMapOf()

    override fun onEnable() {
        instance = this

        saveDefaultConfig()

        CommandHandler.registerClass(ReloadCommand.javaClass)
        CommandHandler.registerClass(WipeCommand.javaClass)
        CommandHandler.registerClass(CosmeticsCommand.javaClass)
        CommandHandler.registerClass(CosmeticsListCommand.javaClass)
        CommandHandler.registerClass(TracksCommand.javaClass)
        CommandHandler.registerClass(TracksEditorCommand.javaClass)

        server.pluginManager.registerEvents(ProfileLoadListeners, this)
        server.pluginManager.registerEvents(ProfileCosmeticListeners, this)
        server.pluginManager.registerEvents(ProfileTrackListeners, this)

        ProfileHandler.initialLoad()
        TrackHandler.initialLoad()

        for (category in categories) {
            if (category is Listener) {
                server.pluginManager.registerEvents(category, this)
            }

            for (cosmetic in category.getCosmetics()) {
                if (cosmetic is Listener) {
                    server.pluginManager.registerEvents(cosmetic, this)
                }

                cosmetics[cosmetic.getID().toLowerCase()] = cosmetic
            }
        }

        CosmeticsThread().start()
    }

    fun getCosmeticById(id: String): Cosmetic? {
        return cosmetics[id.toLowerCase()]
    }

    fun openMenu(player: Player) {
        CategoriesMenu().openMenu(player)
    }

    fun getStorageDatabase(): String {
        return config.getString("storage.database", "Cosmetics")
    }

    fun getStorageCollection(): String {
        return config.getString("storage.collection", "profiles")
    }

    fun isGameServer(): Boolean {
        return config.getBoolean("game-server", true)
    }

}