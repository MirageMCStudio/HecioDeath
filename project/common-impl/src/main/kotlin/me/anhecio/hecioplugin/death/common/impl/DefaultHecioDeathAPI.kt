package me.anhecio.hecioplugin.death.common.impl

import me.anhecio.hecioplugin.death.common.HecioDeathAPI
import me.anhecio.hecioplugin.death.common.HecioDeathMatcherHandler
import me.anhecio.hecioplugin.death.common.HecioDeathPenaltyHandler
import taboolib.common.platform.PlatformFactory

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl
 *
 * @author Anhecio
 * @since 2025/5/31 11:26
 */
class DefaultHecioDeathAPI : HecioDeathAPI {

    /** 玩家匹配器接口 */
    var localMatcher = PlatformFactory.getAPI<HecioDeathMatcherHandler>()

    /** 玩家惩罚器接口 */
    var localPenalty = PlatformFactory.getAPI<HecioDeathPenaltyHandler>()

    override fun getMatcher(): HecioDeathMatcherHandler {
        return localMatcher
    }

    override fun getPenalty(): HecioDeathPenaltyHandler {
        return localPenalty
    }

}