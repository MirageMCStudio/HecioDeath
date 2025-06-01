package me.anhecio.hecioplugin.death.common.util

import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.util
 *
 * @author Anhecio
 * @since 2025/5/31 18:24
 */
fun String.parseToSlotType(): PlayerSlotType {
    return when (this.lowercase()) {
        "arcartx" -> PlayerSlotType.ArcartX
        "dragoncore" -> PlayerSlotType.DragonCore
        "germplugin" -> PlayerSlotType.GermPlugin
        "vanilla" -> PlayerSlotType.Vanilla
        else -> error("未知的槽位类型: $this")
    }
}

fun String.parseToTerritoryType(): PlayerTerritoryType {
    return when (this.lowercase()) {
        "dominion" -> PlayerTerritoryType.Dominion
        "worldguard" -> PlayerTerritoryType.WorldGuard
        "residence" -> PlayerTerritoryType.Residence
        else -> error("未知的领地类型: $this")
    }
}