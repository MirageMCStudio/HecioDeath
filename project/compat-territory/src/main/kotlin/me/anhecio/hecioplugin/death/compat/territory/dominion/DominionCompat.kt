package me.anhecio.hecioplugin.death.compat.territory.dominion

import cn.lunadeer.dominion.api.DominionAPI
import me.anhecio.hecioplugin.death.common.compat.PlayerTerritoryCompat
import me.anhecio.hecioplugin.death.common.compat.data.CompatTerritory
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType
import me.anhecio.hecioplugin.death.compat.territory.DefaultHecioDeathPlayerTerritoryController
import org.bukkit.entity.Player
import taboolib.common.util.unsafeLazy

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.territory.dominion
 *
 * @author Anhecio
 * @since 2025/5/31 20:12
 */
object DominionCompat : PlayerTerritoryCompat {

    /**
     * 获取实例
     */
    private val dominionAPI by lazy { DominionAPI.getInstance() }

    override fun getLocationTerritory(player: Player): CompatTerritory? {
        val territory = dominionAPI.getPlayerCurrentDominion(player)?.id
        return territory?.let {
            DefaultHecioDeathPlayerTerritoryController().toCompatTerritory(territory.toString(), PlayerTerritoryType.Dominion)
        }
    }
}