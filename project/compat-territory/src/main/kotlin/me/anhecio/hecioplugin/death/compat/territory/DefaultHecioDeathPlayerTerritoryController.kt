package me.anhecio.hecioplugin.death.compat.territory

import me.anhecio.hecioplugin.death.common.HecioDeathPlayerTerritoryController
import me.anhecio.hecioplugin.death.common.HecioDeathSettings
import me.anhecio.hecioplugin.death.common.compat.data.CompatTerritory
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType
import me.anhecio.hecioplugin.death.common.compat.type.PlayerTerritoryType.*
import me.anhecio.hecioplugin.death.common.util.parseToTerritoryType
import me.anhecio.hecioplugin.death.compat.territory.dominion.DominionCompat
import me.anhecio.hecioplugin.death.compat.territory.residence.ResidenceCompat
import me.anhecio.hecioplugin.death.compat.territory.worldground.WorldGroundCompat
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.territory
 *
 * @author Anhecio
 * @since 2025/5/31 19:56
 */
class DefaultHecioDeathPlayerTerritoryController : HecioDeathPlayerTerritoryController {
    override val types = mutableSetOf<PlayerTerritoryType>()

    override fun toCompatTerritory(territoryId: String, territoryType: PlayerTerritoryType): CompatTerritory {
        return CompatTerritory(territoryId, territoryType)
    }

    override fun PlayerTerritoryType.isEnabled(): Boolean = types.contains(this)

    override fun getLocationTerritory(player: Player): CompatTerritory? {
        HecioDeathSettings.compatTerritory.forEach { territory ->
            val type = territory.parseToTerritoryType()
            if (!type.isEnabled()) error("领地插件未挂钩: $type")
            return when (type) {
                WorldGround -> WorldGroundCompat.getLocationTerritory(player)
                Residence -> ResidenceCompat.getLocationTerritory(player)
                Dominion -> DominionCompat.getLocationTerritory(player)
            }
        }
        error("未启用领地插件兼容.")
    }

    companion object {
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathPlayerTerritoryController>(DefaultHecioDeathPlayerTerritoryController())
        }
        @Awake(LifeCycle.ACTIVE)
        fun active() {
            HecioDeathSettings.compatTerritory.forEach { compat ->
                val type = compat.parseToTerritoryType()
                if (Bukkit.getPluginManager().isPluginEnabled(type.toString())) {
                    DefaultHecioDeathPlayerTerritoryController().types.add(type)
                    console().sendLang("Plugin-Hooker", type.toString())
                }
            }
        }
    }
}