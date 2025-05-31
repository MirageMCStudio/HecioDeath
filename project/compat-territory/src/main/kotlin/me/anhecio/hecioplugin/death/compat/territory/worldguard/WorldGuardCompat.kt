package me.anhecio.hecioplugin.death.compat.territory.worldguard

import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import me.anhecio.hecioplugin.death.common.compat.PlayerTerritoryCompat
import me.anhecio.hecioplugin.death.common.compat.data.CompatTerritory
import org.bukkit.entity.Player
import com.sk89q.worldguard.WorldGuard
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType
import me.anhecio.hecioplugin.death.compat.territory.DefaultHecioDeathPlayerTerritoryController

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.territory.worldguard
 *
 * @author Anhecio
 * @since 2025/5/31 20:10
 */
object WorldGuardCompat : PlayerTerritoryCompat {

    /**
     * 获取实例
     */
    private val instance by lazy { WorldGuard.getInstance() }

    override fun getLocationTerritory(player: Player): CompatTerritory? {
        val worldGuardManager = instance.platform.regionContainer.get(BukkitAdapter.adapt(player.world))
        val loc = player.location
        val position = BlockVector3.at(loc.blockX, loc.blockY, loc.blockZ)
        val region = worldGuardManager?.getApplicableRegions(position)?.firstOrNull()
        return region?.let {
            DefaultHecioDeathPlayerTerritoryController().toCompatTerritory(it.id, PlayerTerritoryType.WorldGuard)
        }
    }
}