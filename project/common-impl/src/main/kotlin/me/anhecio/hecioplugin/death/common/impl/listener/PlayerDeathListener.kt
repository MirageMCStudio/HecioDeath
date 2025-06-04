package me.anhecio.hecioplugin.death.common.impl.listener

import me.anhecio.hecioplugin.death.common.event.HecioDeathPenaltyEvent
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.platform.Awake
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.listener
 *
 * @author Anhecio
 * @since 2025/5/31 15:48
 */
@SubscribeEvent(priority = EventPriority.MONITOR)
fun listener(event: PlayerDeathEvent) = HecioDeathPenaltyEvent.PreEvent(event).call()
