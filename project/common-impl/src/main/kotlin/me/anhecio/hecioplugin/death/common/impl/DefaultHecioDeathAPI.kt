package me.anhecio.hecioplugin.death.common.impl

import me.anhecio.hecioplugin.death.common.HecioDeathAPI
import me.anhecio.hecioplugin.death.common.HecioDeathItemMatcher
import me.anhecio.hecioplugin.death.common.HecioDeathJavaScriptHandler
import me.anhecio.hecioplugin.death.common.HecioDeathMatcherHandler
import me.anhecio.hecioplugin.death.common.HecioDeathPenaltyHandler
import me.anhecio.hecioplugin.death.common.HecioDeathPlayerSlotController
import me.anhecio.hecioplugin.death.common.HecioDeathPlayerTerritoryController
import me.anhecio.hecioplugin.death.common.HecioDeathSettings
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

    /** 物品匹配器接口 */
    var localItemMatcher = PlatformFactory.getAPI<HecioDeathItemMatcher>()

    /** 槽位控制器接口 */
    var localPlayerSlotController = PlatformFactory.getAPI<HecioDeathPlayerSlotController>()

    /** JavaScript 脚本控制器接口 */
    var localJavaScriptHandler = PlatformFactory.getAPI<HecioDeathJavaScriptHandler>()

    /** 领地控制器接口 */
    var localPlayerTerritoryController = PlatformFactory.getAPI<HecioDeathPlayerTerritoryController>()

    override fun getMatcher(): HecioDeathMatcherHandler {
        return localMatcher
    }

    override fun getPenalty(): HecioDeathPenaltyHandler {
        return localPenalty
    }

    override fun getItemMatcher(): HecioDeathItemMatcher {
        return localItemMatcher
    }

    override fun getPlayerSlotController(): HecioDeathPlayerSlotController {
        return localPlayerSlotController
    }

    override fun getPlayerTerritoryController(): HecioDeathPlayerTerritoryController {
        return localPlayerTerritoryController
    }

    override fun getJavaScriptHandler(): HecioDeathJavaScriptHandler {
        return localJavaScriptHandler
    }

}