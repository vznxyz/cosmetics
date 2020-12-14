package net.evilblock.cosmetics.category.track.util

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs
import kotlin.math.sqrt

object TrackUtil {

    private var switchable: EnumMap<Material, Int> = EnumMap(Material::class.java)

    @JvmStatic
    fun getSwitchableBlockTypes(): EnumMap<Material, Int> {
        return switchable
    }

    @JvmStatic
    fun isSwitchableBlockType(material: Material): Boolean {
        return when (material) {
            Material.STONE, Material.GRASS, Material.DIRT, Material.COBBLESTONE, Material.WOOD, Material.SAND, Material.GRAVEL, Material.GOLD_ORE, Material.IRON_ORE, Material.COAL_ORE, Material.LOG, Material.LOG_2, Material.LEAVES, Material.LEAVES_2, Material.SPONGE, Material.GLASS, Material.LAPIS_ORE, Material.LAPIS_BLOCK, Material.SANDSTONE, Material.WOOL, Material.GOLD_BLOCK, Material.IRON_BLOCK, Material.BRICK, Material.BOOKSHELF, Material.MOSSY_COBBLESTONE, Material.OBSIDIAN, Material.DIAMOND_ORE, Material.DIAMOND_BLOCK, Material.REDSTONE_BLOCK, Material.GLOWING_REDSTONE_ORE, Material.CLAY, Material.PUMPKIN, Material.NETHERRACK, Material.STAINED_GLASS, Material.SMOOTH_BRICK, Material.REDSTONE_ORE, Material.MELON_BLOCK, Material.ENDER_STONE, Material.EMERALD_ORE, Material.EMERALD_BLOCK, Material.QUARTZ_ORE, Material.QUARTZ_BLOCK, Material.STAINED_CLAY, Material.HARD_CLAY, Material.COAL_BLOCK, Material.HAY_BLOCK, Material.REDSTONE_LAMP_OFF, Material.REDSTONE_LAMP_ON, Material.MYCEL, Material.BLACK_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA, Material.SILVER_GLAZED_TERRACOTTA, Material.WHITE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA -> true
            else -> false
        }
    }

    @JvmStatic
    fun canSetItemData(material: Material): Boolean {
        return when (material) {
            Material.STAINED_GLASS, Material.STAINED_CLAY, Material.WOOL -> true
            else -> false
        }
    }

    @JvmStatic
    fun getNewTrail(location: Location, useY: Boolean, radius: Int): Set<Location> {
        val locations: HashSet<Location> = HashSet()

        for (x in -radius..radius) {
            for (y in (if (useY) -radius else 0)..if (useY) radius else 0) {
                for (z in -radius..radius) {
                    val block: Block = location.world.getBlockAt(location.blockX + x, location.blockY + y, location.blockZ + z)
                    if (isSwitchableBlockType(block.type)) {
                        locations.add(block.location)
                    }
                }
            }
        }

        var extraRadius = if (useY) 5 else 3
        for (x in -radius..radius) {
            for (y in (if (useY) -radius else 0)..if (useY) radius else 0) {
                for (z in -radius..radius) {
                    if (abs(x) <= radius && abs(y) <= radius && abs(z) <= radius) {
                        continue
                    }

                    if (extraRadius > 0) {
                        if (30.0 > ThreadLocalRandom.current().nextDouble() * 100.0) {
                            val block: Block = location.world.getBlockAt(location.blockX + x, location.blockY + y, location.blockZ + z)
                            if (isSwitchableBlockType(block.type)) {
                                locations.add(block.location)
                                extraRadius--
                            }
                        }
                    } else {
                        return locations
                    }
                }
            }
        }

        return locations
    }

    @JvmStatic
    fun getNearbyPlayers(location: Location, maxDistance: Double): Collection<Player>? {
        val players: MutableList<Player> = arrayListOf()

        for (player in Bukkit.getOnlinePlayers()) {
            val xDis = player.location.x - location.x
            val zDis = player.location.z - location.z
            if (player.world == location.world && sqrt(xDis * xDis + zDis * zDis) <= maxDistance) {
                players.add(player)
            }
        }

        return players
    }

    init {
        switchable = EnumMap(Material::class.java)
        switchable[Material.STONE] = 6
        switchable[Material.GRASS] = 0
        switchable[Material.DIRT] = 2
        switchable[Material.COBBLESTONE] = 0
        switchable[Material.WOOD] = 5
        switchable[Material.SAND] = 1
        switchable[Material.GRAVEL] = 0
        switchable[Material.GOLD_ORE] = 0
        switchable[Material.IRON_ORE] = 0
        switchable[Material.COAL_ORE] = 0
        switchable[Material.LOG] = 3
        switchable[Material.LEAVES] = 3
        switchable[Material.SPONGE] = 0
        switchable[Material.LAPIS_ORE] = 0
        switchable[Material.LAPIS_BLOCK] = 0
        switchable[Material.SANDSTONE] = 2
        switchable[Material.WOOL] = 15
        switchable[Material.GOLD_BLOCK] = 0
        switchable[Material.IRON_BLOCK] = 0
        switchable[Material.BRICK] = 0
        switchable[Material.BOOKSHELF] = 0
        switchable[Material.MOSSY_COBBLESTONE] = 0
        switchable[Material.OBSIDIAN] = 0
        switchable[Material.DIAMOND_ORE] = 0
        switchable[Material.DIAMOND_BLOCK] = 0
        switchable[Material.REDSTONE_ORE] = 0
        switchable[Material.SNOW_BLOCK] = 0
        switchable[Material.ICE] = 0
        switchable[Material.CLAY] = 0
        switchable[Material.NETHERRACK] = 0
        switchable[Material.GLOWSTONE] = 0
        switchable[Material.STAINED_GLASS] = 15
        switchable[Material.SMOOTH_BRICK] = 3
        switchable[Material.MYCEL] = 0
        switchable[Material.ENDER_STONE] = 0
        switchable[Material.NETHER_BRICK] = 0
        switchable[Material.EMERALD_ORE] = 0
        switchable[Material.EMERALD_BLOCK] = 0
        switchable[Material.REDSTONE_BLOCK] = 0
        switchable[Material.QUARTZ_ORE] = 0
        switchable[Material.QUARTZ_BLOCK] = 2
        switchable[Material.STAINED_CLAY] = 15
        switchable[Material.LEAVES_2] = 1
        switchable[Material.LOG_2] = 1
        switchable[Material.PRISMARINE] = 2
        switchable[Material.SEA_LANTERN] = 0
        switchable[Material.HAY_BLOCK] = 0
        switchable[Material.COAL_BLOCK] = 0
        switchable[Material.PACKED_ICE] = 0
        switchable[Material.RED_SANDSTONE] = 2
        switchable[Material.PURPUR_BLOCK] = 0
        switchable[Material.PURPUR_PILLAR] = 0
        switchable[Material.NETHER_WART_BLOCK] = 0
        switchable[Material.RED_NETHER_BRICK] = 0
        switchable[Material.BONE_BLOCK] = 0
        switchable[Material.WHITE_SHULKER_BOX] = 0
        switchable[Material.ORANGE_SHULKER_BOX] = 0
        switchable[Material.MAGENTA_SHULKER_BOX] = 0
        switchable[Material.LIGHT_BLUE_SHULKER_BOX] = 0
        switchable[Material.YELLOW_SHULKER_BOX] = 0
        switchable[Material.LIME_SHULKER_BOX] = 0
        switchable[Material.PINK_SHULKER_BOX] = 0
        switchable[Material.GRAY_SHULKER_BOX] = 0
        switchable[Material.SILVER_SHULKER_BOX] = 0
        switchable[Material.CYAN_SHULKER_BOX] = 0
        switchable[Material.PURPLE_SHULKER_BOX] = 0
        switchable[Material.BLUE_SHULKER_BOX] = 0
        switchable[Material.BROWN_SHULKER_BOX] = 0
        switchable[Material.GREEN_SHULKER_BOX] = 0
        switchable[Material.RED_SHULKER_BOX] = 0
        switchable[Material.BLACK_SHULKER_BOX] = 0
        switchable[Material.WHITE_GLAZED_TERRACOTTA] = 0
        switchable[Material.ORANGE_GLAZED_TERRACOTTA] = 0
        switchable[Material.MAGENTA_GLAZED_TERRACOTTA] = 0
        switchable[Material.LIGHT_BLUE_GLAZED_TERRACOTTA] = 0
        switchable[Material.YELLOW_GLAZED_TERRACOTTA] = 0
        switchable[Material.LIME_GLAZED_TERRACOTTA] = 0
        switchable[Material.PINK_GLAZED_TERRACOTTA] = 0
        switchable[Material.GRAY_GLAZED_TERRACOTTA] = 0
        switchable[Material.SILVER_GLAZED_TERRACOTTA] = 0
        switchable[Material.CYAN_GLAZED_TERRACOTTA] = 0
        switchable[Material.PURPLE_GLAZED_TERRACOTTA] = 0
        switchable[Material.BLUE_GLAZED_TERRACOTTA] = 0
        switchable[Material.BROWN_GLAZED_TERRACOTTA] = 0
        switchable[Material.GREEN_GLAZED_TERRACOTTA] = 0
        switchable[Material.RED_GLAZED_TERRACOTTA] = 0
        switchable[Material.BLACK_GLAZED_TERRACOTTA] = 0
        switchable[Material.CONCRETE] = 15
        switchable[Material.CONCRETE_POWDER] = 15
    }

}