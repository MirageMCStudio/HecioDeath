package me.anhecio.hecioplugin.death.module.matcher.handler

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.util.debug
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
 * @since 2025/6/1 20:48
 */
object JavaScriptMatcherHandler : MatcherHandler {
    override val name: String = "JavaScript"

    override fun match(context: Map<String, Any?>, config: ConfigurationSection): Boolean {

        val script = config.getString("run") ?: return true
        val result = HecioDeath.api().getJavaScriptHandler().run(script, context)
        return when (result) {
            is Boolean -> result
            else -> true
        }
    }

    @Awake(LifeCycle.CONST)
    fun register() {
        MatcherRegistry.register(this)
    }
}