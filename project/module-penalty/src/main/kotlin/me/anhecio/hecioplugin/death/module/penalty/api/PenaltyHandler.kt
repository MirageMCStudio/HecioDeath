package me.anhecio.hecioplugin.death.module.penalty.api

import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.api
 *
 * @author Anhecio
 * @since 2025/6/3 17:04
 */
interface PenaltyHandler {
    val name: String

    fun penalty(context: Map<String, Any?>, config: ConfigurationSection)

}