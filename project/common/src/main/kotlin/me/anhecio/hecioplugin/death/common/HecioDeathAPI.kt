package me.anhecio.hecioplugin.death.common

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 11:08
 */
interface HecioDeathAPI {
    /**
     * 获取用于玩家匹配的匹配器接口
     */
    fun getMatcher(): HecioDeathMatcherHandler

    /**
     * 获取用于对玩家惩罚的惩罚器接口
     */
    fun getPenalty(): HecioDeathPenaltyHandler

    /**
     * 获取用于物品匹配的物品匹配器接口
     */
    fun getItemMatcher(): HecioDeathItemMatcher

    /**
     * 获取用于控制玩家槽位的接口
     */
    fun getPlayerSlotController(): HecioDeathPlayerSlotController

    /**
     * 获取用于控制玩家领地的接口
     */
    fun getPlayerTerritoryController(): HecioDeathPlayerTerritoryController

    /**
     * 获取用于控制 JavaScript 的脚本接口
     */
    fun getJavaScriptHandler(): HecioDeathJavaScriptHandler

}