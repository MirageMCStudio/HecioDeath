package me.anhecio.hecioplugin.death.common.compat

import me.anhecio.hecioplugin.death.common.compat.data.CompatTerritory
import org.bukkit.entity.Player

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.compat
 *
 * @author Anhecio
 * @since 2025/5/31 20:13
 */
interface PlayerTerritoryCompat {
    /**
     * 获取指定玩家当前所处的领地名
     */
    fun getLocationTerritory(player: Player): CompatTerritory?
}