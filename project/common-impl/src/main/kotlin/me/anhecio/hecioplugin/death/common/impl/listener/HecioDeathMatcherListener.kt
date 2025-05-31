package me.anhecio.hecioplugin.death.common.impl.listener

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.event.HecioDeathMatcherEvent
import me.anhecio.hecioplugin.death.common.event.HecioDeathPenaltyEvent
import me.anhecio.hecioplugin.death.common.util.debug
import taboolib.common.platform.event.SubscribeEvent

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.listener
 *
 * @author Anhecio
 * @since 2025/5/31 15:51
 */
@SubscribeEvent
fun listener(event: HecioDeathMatcherEvent) {
    debug {
        val name = event.event.entity.player!!.name
        debug("玩家 $name 触发了 HecioDeathMatcherEvent 事件.")
    }
    if (HecioDeath.api().getMatcher().matcher(event.event, event.matcherConfig)) {
        HecioDeathPenaltyEvent.PostEvent(event.event, event.penaltyId).call()
    }
}