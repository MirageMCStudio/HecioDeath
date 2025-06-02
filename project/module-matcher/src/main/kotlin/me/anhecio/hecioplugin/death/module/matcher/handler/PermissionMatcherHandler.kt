package me.anhecio.hecioplugin.death.module.matcher.handler

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
 * @since 2025/5/31 17:28
 */
object PermissionMatcherHandler : MatcherHandler {
    override val name: String = "Permission"

    override fun match(context: Map<String, Any?>, config: ConfigurationSection): Boolean {
        val requires = config.getConfigurationSection("requires") ?: return true
        val player = (context["player"] ?: error("不存在待匹配玩家")) as Player

        // "has": 玩家拥有 requires 中的所有权限
        requires.getStringList("has").takeIf { it.isNotEmpty() }?.let { permList->
            if (permList.any { !player.hasPermission(it) }) return false
        }

        // "has-only": 玩家拥有 require 中的任意一个权限
        requires.getStringList("has-only").takeIf { it.isNotEmpty() }?.let { permList->
            if (permList.none { player.hasPermission(it) }) return false
        }

        // "lack": 玩家缺少 requires 中的所有权限
        requires.getStringList("lack").takeIf { it.isNotEmpty() }?.let { permList->
            if (permList.any { player.hasPermission(it) }) return false
        }

        // "lack-only": 玩家缺少 requires 中的 任意一个权限
        requires.getStringList("lack-only").takeIf { it.isNotEmpty() }?.let { permList->
            if (permList.none { !player.hasPermission(it) }) return false
        }

        return true
    }
    @Awake(LifeCycle.CONST)
    fun register() {
        MatcherRegistry.register(this)
    }
}