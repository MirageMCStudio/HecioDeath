package me.anhecio.hecioplugin.death.common.impl.listener

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.event.HecioDeathMatcherEvent
import me.anhecio.hecioplugin.death.common.event.HecioDeathPenaltyEvent
import me.anhecio.hecioplugin.death.common.util.debug
import org.bukkit.entity.Player
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
        debug("开始处理玩家 $name 的死亡事件.")
        debug("玩家 $name 触发了 HecioDeathPenaltyEvent.PreEvent 事件.")
    }
    val penaltyHandler = HecioDeath.api().getPenalty()

    val context = mutableMapOf<String, Any>()
    context.put("event", event.event)
    context.put("player", event.event.entity.player!!)

    penaltyHandler.getConfigManager().cache.forEach { (penaltyId, _) ->
        HecioDeathMatcherEvent(context, penaltyHandler.getBindMatchers(penaltyId), penaltyId).call()

    }

}

@SubscribeEvent
fun post(event: HecioDeathPenaltyEvent.PostEvent) {
    debug {
        val name = (event.context["player"] as Player).name
        debug {
            val id = event.penaltyId
            debug("玩家 $name 触发了 HecioDeathPenaltyEvent.PostEvent 事件: $id")
        }
    }





    debug {
        val name = (event.context["player"] as Player).name
        debug {
            val id = event.penaltyId
            debug("玩家 $name 的死亡事件处理完毕.")
        }
    }
}