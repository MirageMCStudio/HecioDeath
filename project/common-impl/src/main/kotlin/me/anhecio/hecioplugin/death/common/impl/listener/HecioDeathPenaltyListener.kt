package me.anhecio.hecioplugin.death.common.impl.listener

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.HecioDeathSettings
import me.anhecio.hecioplugin.death.common.event.HecioDeathMatcherEvent
import me.anhecio.hecioplugin.death.common.event.HecioDeathPenaltyEvent
import me.anhecio.hecioplugin.death.common.util.debug
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

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

    val manager = penaltyHandler.getParsedConfigManager()

    manager.forEach { (penaltyId, _) ->
        val bind = penaltyHandler.getBindMatchers(penaltyId)
        if (bind == null) return@forEach
        val matcher = HecioDeathMatcherEvent(context, bind, penaltyId)
        matcher.call()
        if (matcher.matched) return
    }

    debug {
        val name = event.event.entity.player!!.name
        debug("玩家 $name 不存在匹配的惩罚器配置.")
    }

    if (!HecioDeathSettings.default.isEmpty()) {
        debug("检测到开启了默认配置项: ${HecioDeathSettings.default}")
        HecioDeathPenaltyEvent.PostEvent(context, HecioDeathSettings.default).call()
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

    // 把原版的死亡事件的东西能关的都关了
    val deathEvent = event.context["event"] as PlayerDeathEvent
    debug("开始尝试关闭原版死亡事件相关动作...")
    disableVanillaDeathActions(deathEvent)
    debug("成功关闭原版死亡事件相关动作.")

    // 执行插件惩罚事件逻辑
    debug {
        val name = (event.context["player"] as Player).name
        debug("开始为玩家 $name 执行惩罚器: ${event.penaltyId}")
    }


    HecioDeath.api().getPenalty().penalty(event.context, event.penaltyId)


    debug("└─ 惩罚器执行完毕.")

    debug {
        val name = (event.context["player"] as Player).name
        debug("玩家 $name 的死亡事件处理完毕.")
    }
}

/**
 * 关闭原版死亡事件相关动作
 */
fun disableVanillaDeathActions(event: PlayerDeathEvent) {

    // 关闭背包掉落
    event.keepInventory = true
    // 关闭等级掉落
    event.keepLevel = true
    // 关闭死亡消息
    event.deathMessage = null

}