package me.anhecio.hecioplugin.death.module.penalty.handler.level.data

import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.LevelPenaltyType
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.LevelStrategyType
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.LevelUnitType

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.level.data
 *
 * @author Anhecio
 * @since 2025/6/4 13:21
 */
data class LevelData(
    val mode: LevelPenaltyType = LevelPenaltyType.DROP,
    val unit: LevelUnitType = LevelUnitType.EXPERIENCE,
    val strategy: LevelStrategyType = LevelStrategyType.ALL,
    val didnt: Number = 0,
    val value: String = "",
)