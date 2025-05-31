package me.anhecio.hecioplugin.death.module.matcher.api

import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.module.penalty.api
 *
 * @author Anhecio
 * @since 2025/5/31 17:09
 */
interface MatcherHandler {

    val name: String

    fun match(event: PlayerDeathEvent, config: ConfigurationSection): Boolean

}