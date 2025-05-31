package me.anhecio.hecioplugin.death.module.matcher.handler

import me.anhecio.hecioplugin.death.module.matcher.api.MatcherHandler
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import sun.audio.AudioPlayer.player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.matcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 17:25
 */
object NameMatcherHandler : MatcherHandler {
    override val name: String = "Name"

    override fun match(event: PlayerDeathEvent, config: ConfigurationSection): Boolean {
        val requires = config.getConfigurationSection("requires") ?: return true
        val player = event.entity.player!!
        // "in": 玩家名是 requires 中的任意一个
        requires.getStringList("in").takeIf { it.isNotEmpty() }?.let { nameList ->
            if (player.name !in nameList) return false
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