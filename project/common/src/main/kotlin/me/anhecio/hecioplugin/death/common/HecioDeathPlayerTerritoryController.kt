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
     * 启用的领地类型
     */
    val types: MutableSet<PlayerTerritoryType>

    /**
     * 解析领地Id为CompatTerritory
     */
    fun toCompatTerritory(territoryId: String, territoryType: PlayerTerritoryType): CompatTerritory

    /**
     * 判断指定领地是否启用
     */
    fun PlayerTerritoryType.isEnabled(): Boolean

    /**
     * 获取玩家当前处在的领地
     */
    fun getLocationTerritory(player: Player): CompatTerritory?
}