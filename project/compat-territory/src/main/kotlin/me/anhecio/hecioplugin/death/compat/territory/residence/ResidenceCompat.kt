package me.anhecio.hecioplugin.death.compat.territory.residence

import com.bekvon.bukkit.residence.Residence
import me.anhecio.hecioplugin.death.common.compat.PlayerTerritoryCompat
import me.anhecio.hecioplugin.death.common.compat.data.CompatTerritory
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType
import me.anhecio.hecioplugin.death.compat.territory.DefaultHecioDeathPlayerTerritoryController
import org.bukkit.entity.Player

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.territory.residence
 *
 * @author Anhecio
 * @since 2025/5/31 20:11
 */
object ResidenceCompat : PlayerTerritoryCompat {

    /**
     * 获取实例
     */
    private val manager by lazy { Residence.getInstance().residenceManager }

    override fun getLocationTerritory(player: Player): CompatTerritory? {
        val res = manager.getByLoc(player.location)
        if (res == null) return null
        return DefaultHecioDeathPlayerTerritoryController().toCompatTerritory(res.name, PlayerTerritoryType.Residence)
    }

}