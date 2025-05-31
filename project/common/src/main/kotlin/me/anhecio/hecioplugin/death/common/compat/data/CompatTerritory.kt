package me.anhecio.hecioplugin.death.common.compat.data

import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.compat.data
 *
 * @author Anhecio
 * @since 2025/5/31 20:54
 */
data class CompatTerritory(
    val territoryId: String,
    val territoryType: PlayerTerritoryType,
)