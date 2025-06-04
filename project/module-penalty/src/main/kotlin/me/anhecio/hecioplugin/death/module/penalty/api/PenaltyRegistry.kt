package me.anhecio.hecioplugin.death.module.penalty.api

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.api
 *
 * @author Anhecio
 * @since 2025/6/3 17:04
 */
object PenaltyRegistry {
    private val handlers = mutableMapOf<String, PenaltyHandler>()

    fun register(handler: PenaltyHandler) {
        handlers[handler.name.lowercase()] = handler
    }

    fun get(name: String): PenaltyHandler? = handlers[name.lowercase()]
}