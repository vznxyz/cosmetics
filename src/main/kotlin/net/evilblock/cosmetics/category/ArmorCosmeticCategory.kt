package net.evilblock.cosmetics.category

import net.evilblock.cosmetics.Cosmetic
import net.evilblock.cosmetics.CosmeticCategory
import net.evilblock.cosmetics.category.armor.RainbowArmorCosmetic
import net.evilblock.cosmetics.category.armor.RankArmorCosmetic
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object ArmorCosmeticCategory : CosmeticCategory {

    private val cosmetics = arrayListOf(
        RainbowArmorCosmetic(),
        RankArmorCosmetic(
                id = "owner",
                rankName = "${ChatColor.DARK_RED}${ChatColor.BOLD}Owner",
                permission = "rank.owner",
                hidden = true,
                color = Color.fromRGB(156, 10, 8)
        ),
        RankArmorCosmetic(
                id = "admin",
                rankName = "${ChatColor.RED}${ChatColor.BOLD}Admin",
                permission = "rank.admin",
                hidden = true,
                color = Color.fromRGB(209, 35, 29)
        ),
        RankArmorCosmetic(
                id = "mod",
                rankName = "${ChatColor.DARK_PURPLE}${ChatColor.BOLD}Mod",
                permission = "rank.moderator",
                hidden = true,
                color = Color.PURPLE
        ),
        RankArmorCosmetic(
                id = "youtuber",
                rankName = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}YouTuber",
                permission = "rank.youtuber",
                hidden = false,
                color = Color.fromRGB(252, 3, 223)
        ),
        RankArmorCosmetic(
                id = "junkie-plus",
                rankName = "${ChatColor.LIGHT_PURPLE}${ChatColor.BOLD}Junkie${ChatColor.GREEN}${ChatColor.BOLD}+${ChatColor.YELLOW}${ChatColor.BOLD}",
                permission = "rank.junkie-plus",
                hidden = false,
                color = Color.fromRGB(254, 216, 61),
                helmetColor = Color.fromRGB(129,199,32),
                chestplateColor = Color.fromRGB(254, 216, 61),
                leggingsColor = Color.fromRGB(254, 216, 61),
                bootsColor = Color.fromRGB(129,199,32)
        ),
        RankArmorCosmetic(
                id = "junkie",
                rankName = "${ChatColor.YELLOW}${ChatColor.BOLD}Junkie",
                permission = "rank.junkie",
                hidden = false,
                color = Color.fromRGB(254, 216, 61)
        )
//            RankArmourCosmetic("Platinum", "rank.platinum", false, Color.fromRGB(252, 144, 3)),
//            RankArmourCosmetic("Premium", "rank.premium", false, Color.fromRGB(85, 85, 255)),
//            RankArmourCosmetic("Basic", "rank.basic", false, Color.fromRGB(23, 227, 77))
    )

    override fun getIcon(): ItemStack {
        return ItemStack(Material.DIAMOND_CHESTPLATE)
    }

    override fun getName(): String {
        return "Armor Sets"
    }

    override fun getPluralName(): String {
        return "armor sets"
    }

    override fun getCosmetics(): List<Cosmetic> {
        return cosmetics
    }

}