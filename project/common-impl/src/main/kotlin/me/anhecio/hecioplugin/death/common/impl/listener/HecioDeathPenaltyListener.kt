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
fun pre(event: HecioDeathPenaltyEvent.PreEvent) {
    debug {
        val name = event.event.entity.player!!.name
        debug("玩家 $name 触发了 HecioDeathPenaltyEvent.PreEvent 事件.")
    }
    val penaltyHandler = HecioDeath.api().getPenalty()
    penaltyHandler.getConfigManager().cache.forEach { (penaltyId, _) ->
        HecioDeathMatcherEvent(event.event, penaltyHandler.getBindMatchers(penaltyId), penaltyId).call()
    }

}

@SubscribeEvent
fun post(event: HecioDeathPenaltyEvent.PostEvent) {
    debug {
        val name = event.event.entity.player!!.name
        debug {
            val id = event.penaltyId
            debug("玩家 $name 触发了 HecioDeathPenaltyEvent.PostEvent 事件: $id")
        }

    }

}