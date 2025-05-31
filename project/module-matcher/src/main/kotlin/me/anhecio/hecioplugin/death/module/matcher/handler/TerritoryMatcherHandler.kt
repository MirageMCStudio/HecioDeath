package me.anhecio.hecioplugin.death.module.matcher.handler

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.util.parseToTerritoryType
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherHandler
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.matcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 21:34
 */
object TerritoryMatcherHandler : MatcherHandler {
    override val name: String = "Territory"

    override fun match(event: PlayerDeathEvent, config: ConfigurationSection): Boolean {
        val requires = config.getConfigurationSection("requires") ?: return true
        val player = event.entity.player!!
        val territory = HecioDeath.api().getPlayerTerritoryController().getLocationTerritory(player)
        if (territory == null && requires.contains("out")) return true
        else if (territory == null) return false
        // in
        requires.getStringList("in").takeIf { it.isNotEmpty() }?.let { territoryList ->
            territoryList.forEach { compat ->
                val (type, id) = compat.split("::")
                if (type.parseToTerritoryType() != territory.territoryType || id != territory.territoryId){
                    return false
                }
            }
        }

        // out
        requires.getStringList("out").takeIf { it.isNotEmpty() }?.let { territoryList ->
            territoryList.forEach { compat ->
                val (type, id) = compat.split("::")
                if (type.parseToTerritoryType() == territory.territoryType && id == territory.territoryId){
                    return false
                }
            }
        }
        return true
    }
    @Awake(LifeCycle.CONST)
    fun register() {
        MatcherRegistry.register(this)
    }
}