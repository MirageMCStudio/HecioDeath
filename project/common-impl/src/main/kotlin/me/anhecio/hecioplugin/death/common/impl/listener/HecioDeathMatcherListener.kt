package me.anhecio.hecioplugin.death.common.impl.listener

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.event.HecioDeathMatcherEvent
import me.anhecio.hecioplugin.death.common.event.HecioDeathPenaltyEvent
import me.anhecio.hecioplugin.death.common.util.debug
import org.bukkit.entity.Player
import sun.audio.AudioPlayer.player
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
        val name = (event.context["player"] as Player).name
        debug("玩家 $name 触发了 HecioDeathMatcherEvent 事件.")
        debug("|- 开始为惩罚器 ${event.penaltyId} 执行绑定的匹配器配置.")
    }
    if (HecioDeath.api().getMatcher().matcher(event.context, event.matcherConfig)) {
        debug("└─ 惩罚器匹配成功.")
        event.matched = true
        HecioDeathPenaltyEvent.PostEvent(event.context, event.penaltyId).call()
    } else {
        debug("└─ 惩罚器匹配失败.")
    }
}