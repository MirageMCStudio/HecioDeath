package me.anhecio.hecioplugin.death.module.matcher.handler

import me.anhecio.hecioplugin.death.common.util.debug
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherHandler
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection

object WorldMatcherHandler : MatcherHandler  {
    override val name: String = "World"

    override fun match(context: Map<String, Any?>, config: ConfigurationSection): Boolean {
        val requires = config.getConfigurationSection("requires") ?: return true
        val player = (context["player"] ?: error("不存在待匹配玩家")) as Player

        // "in": 世界名是 requires 中的任意一个
        requires.getStringList("in").takeIf { it.isNotEmpty() }?.let { nameList ->
            if (player.world.name !in nameList) return false
        }

        // "out": 世界名不是 requires 中的任意一个
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