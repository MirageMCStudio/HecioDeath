package me.anhecio.hecioplugin.death.module.penalty.handler.respawn.type

import me.anhecio.hecioplugin.death.module.penalty.handler.respawn.data.RespawnType

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.data
 *
 * @author Anhecio
 * @since 2025/6/3 17:14
 */
data class RespawnData(
    val auto: Boolean = false,
    val mode: RespawnType = RespawnType.SPAWN,
    val location: Location? = null,
)

data class Location(
    val world:  String? = null,
    val x: Number? = null,
    val y: Number? = null,
    val z: Number? = null,
    val yaw: Number? = null,
    val pitch: Number? = null,
)
