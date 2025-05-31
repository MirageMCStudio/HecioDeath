package me.anhecio.hecioplugin.death.compat.territory.worldground

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
 * me.anhecio.hecioplugin.death.compat.territory.worldground
 *
 * @author Anhecio
 * @since 2025/5/31 20:10
 */
object WorldGroundCompat : PlayerTerritoryCompat {

    /**
     * 获取实例
     */
    private val instance by lazy { WorldGuard.getInstance() }

    override fun getLocationTerritory(player: Player): CompatTerritory? {
        val worldGuardManager = instance.platform.regionContainer.get(BukkitAdapter.adapt(player.world))
        val x = player.location.x.toInt()
        val y = player.location.y.toInt()
        val z = player.location.z.toInt()
        val region = worldGuardManager?.getApplicableRegions(BlockVector3(x, y, z))?.firstOrNull()
        if (region == null) return null
        return DefaultHecioDeathPlayerTerritoryController().toCompatTerritory(region.id, PlayerTerritoryType.WorldGround)
    }
}