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

}