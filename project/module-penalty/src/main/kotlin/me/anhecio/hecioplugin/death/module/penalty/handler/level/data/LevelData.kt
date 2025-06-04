package me.anhecio.hecioplugin.death.module.penalty.handler.level.data

import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.AmountPenaltyType
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.AmountStrategyType
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.AmountUnitType

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.level.data
 *
 * @author Anhecio
 * @since 2025/6/4 13:21
 */
data class LevelData(
    val mode: AmountPenaltyType = AmountPenaltyType.DROP,
    val unit: AmountUnitType = AmountUnitType.EXPERIENCE,
    val strategy: AmountStrategyType = AmountStrategyType.ALL,
    val didnt: Number = 0,
    val value: String = "",
)