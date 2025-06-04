package me.anhecio.hecioplugin.death.module.penalty.handler.respawn.listener

import me.anhecio.hecioplugin.death.module.penalty.handler.respawn.RespawnPenaltyHandler
import org.bukkit.event.player.PlayerRespawnEvent
import taboolib.common.platform.event.SubscribeEvent

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.listener
 *
 * @author Anhecio
 * @since 2025/6/4 12:47
 */
@SubscribeEvent
fun listener(event: PlayerRespawnEvent) {
    val uuid = event.player.uniqueId
    val location = RespawnPenaltyHandler.pendingRespawnLocations.remove(uuid) ?: return
    event.respawnLocation = location
}