package me.anhecio.hecioplugin.death.module.penalty.handler.level.type

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.level.type
 *
 * @author Anhecio
 * @since 2025/6/4 13:33
 */
enum class AmountStrategyType {
    /** 指定值 */
    FIXED,
    /** 百分比值 */
    PERCENTAGE,
    /** 范围随机值 */
    RANGE,
    /** 随机值 */
    RANDOM,
    /** 全部 */
    ALL,
    /** 空 */
    NONE,
}