package me.anhecio.hecioplugin.death.common

import me.anhecio.hecioplugin.death.common.compat.PlayerTerritoryCompat
import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import me.anhecio.hecioplugin.death.common.compat.data.CompatTerritory
import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType
import org.bukkit.entity.Player

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 19:56
 */
interface HecioDeathPlayerTerritoryController {
    /**
     * 解析领地Id为CompatTerritory
     */
    fun toCompatTerritory(territoryId: String, territoryType: PlayerTerritoryType): CompatTerritory


    /**
     * 获取玩家当前处在的领地
     */
    fun getLocationTerritory(player: Player): CompatTerritory?
}