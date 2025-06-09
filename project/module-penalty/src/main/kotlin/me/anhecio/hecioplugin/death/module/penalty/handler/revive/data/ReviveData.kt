package me.anhecio.hecioplugin.death.module.penalty.handler.revive.data

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.revive.data
 *
 * @author Anhecio
 * @since 2025/6/5 14:04
 */
data class ReviveData(
    val cooldown: Number = 120,
    val refresh: Number = 1,
    val persistence: Boolean = true
)