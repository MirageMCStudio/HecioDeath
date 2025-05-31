package me.anhecio.hecioplugin.death.common.event

import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.platform.type.BukkitProxyEvent

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.event
 *
 * @author Anhecio
 * @since 2025/5/31 15:42
 */
sealed class HecioDeathPenaltyEvent {
    class PreEvent(val event: PlayerDeathEvent) : BukkitProxyEvent()
    class PostEvent(val event: PlayerDeathEvent, val penaltyId: String) : BukkitProxyEvent()
}