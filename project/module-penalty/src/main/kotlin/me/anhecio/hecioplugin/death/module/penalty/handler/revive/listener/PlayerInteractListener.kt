package me.anhecio.hecioplugin.death.module.penalty.handler.revive.listener

import me.anhecio.hecioplugin.death.module.penalty.handler.revive.RevivePenaltyHandler
import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent


/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.revive.listener
 *
 * @author Anhecio
 * @since 2025/6/5 14:16
 */
@SubscribeEvent
fun onPlayerInteract(event: PlayerInteractEvent) {
    val uuid = event.player.uniqueId
    if (RevivePenaltyHandler.revivalPlayers.containsKey(uuid)) {
        event.isCancelled = true
    }
}