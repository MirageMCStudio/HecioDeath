package me.anhecio.hecioplugin.death.module.matcher.api

/**
 * HecioDeath
 * me.anhecio.hecioplugin.module.penalty.api
 *
 * @author Anhecio
 * @since 2025/5/31 17:07
 */
object MatcherRegistry {
    private val handlers = mutableMapOf<String, MatcherHandler>()

    fun register(handler: MatcherHandler) {
        handlers[handler.name.lowercase()] = handler
    }

    fun get(name: String): MatcherHandler? = handlers[name.lowercase()]
}