package me.anhecio.hecioplugin.death.module.matcher.handler

import me.anhecio.hecioplugin.death.module.matcher.api.MatcherHandler
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection

object WorldMatcherHandler : MatcherHandler  {
    override val name: String = "World"
    
    override fun match(event: PlayerDeathEvent, config: ConfigurationSection): Boolean {
        val requires = config.getConfigurationSection("requires") ?: return true
        val player = event.entity.player!!
        // "in": 玩家名是 requires 中的任意一个
        requires.getStringList("in").takeIf { it.isNotEmpty() }?.let { nameList ->
            if (player.world.name !in nameList) return false
        }

        // "out": 玩家名不是 requires 中的任意一个
        requires.getStringList("out").takeIf { it.isNotEmpty() }?.let { nameList ->
            if (player.world.name in nameList) return false
        }

        return true
    }
    @Awake(LifeCycle.CONST)
    fun register() {
        MatcherRegistry.register(this)
    }
}