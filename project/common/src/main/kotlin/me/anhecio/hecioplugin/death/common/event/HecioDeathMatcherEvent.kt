package me.anhecio.hecioplugin.death.common.event

import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.library.configuration.ConfigurationSection
import taboolib.platform.type.BukkitProxyEvent

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.event
 *
 * @author Anhecio
 * @since 2025/5/31 15:42
 */
class HecioDeathMatcherEvent(val event: PlayerDeathEvent, val matcherConfig: ConfigurationSection, val penaltyId: String) : BukkitProxyEvent()