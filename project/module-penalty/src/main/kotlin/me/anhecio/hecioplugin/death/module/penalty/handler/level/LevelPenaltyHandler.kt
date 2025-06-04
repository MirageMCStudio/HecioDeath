package me.anhecio.hecioplugin.death.module.penalty.handler.level

import me.anhecio.hecioplugin.death.common.util.debug
import me.anhecio.hecioplugin.death.common.util.dropExpAt
import me.anhecio.hecioplugin.death.common.util.getExpFromLevel
import me.anhecio.hecioplugin.death.common.util.getExpToNext
import me.anhecio.hecioplugin.death.common.util.getIntLevelFromExp
import me.anhecio.hecioplugin.death.common.util.toPercentage
import me.anhecio.hecioplugin.death.common.util.toRange
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import me.anhecio.hecioplugin.death.module.penalty.handler.level.data.LevelData
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.AmountPenaltyType.*
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.AmountStrategyType.*
import me.anhecio.hecioplugin.death.module.penalty.handler.level.type.AmountUnitType.*
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.util.random
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration.Companion.toObject
import kotlin.math.max

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.level
 *
 * @author Anhecio
 * @since 2025/6/4 13:20
 */
object LevelPenaltyHandler : PenaltyHandler{
    override val name: String = "Level"

    override fun penalty(context: Map<String, Any?>, config: ConfigurationSection) {
        val executor = config.getConfigurationSection("executor") ?: return
        val data = executor.toObject<LevelData>()
        val player = context["player"] as Player

        debug {
            debug(
                """
                    mode: ${data.mode}
                    unit: ${data.unit}
                    strategy: ${data.strategy}
                    didnt: ${data.didnt}
                    value: ${data.value}
                    """
            )
        }

        /** 玩家当前总经验值 */
        val nowTotalExperience = player.totalExperience
        /** 玩家当前总等级值 */
        val nowTotalLevel = player.level
        
        /** 玩家应处理的等级值 */
        var dropTotalLevel = 0
        /** 玩家应当处理的经验值 */
        var dropTotalExperience = 0

        // 阈值用等级来判断 经验判断没什么意义
        if (nowTotalLevel <= data.didnt.toInt()) return

        when (data.strategy) {
            FIXED -> {
                when (data.unit) {
                    EXPERIENCE -> dropTotalExperience = max(nowTotalExperience, data.value.toInt())
                    LEVEL -> dropTotalLevel = max(nowTotalLevel, data.value.toInt())
                }
            }
            PERCENTAGE -> {
                when (data.unit) {
                    EXPERIENCE -> dropTotalExperience = (nowTotalExperience * (data.value.toPercentage())).toInt()
                    LEVEL -> dropTotalLevel = (nowTotalLevel * (data.value.toPercentage())).toInt()
                }
            }
            RANGE -> {
                when (data.unit) {
                    EXPERIENCE -> dropTotalExperience = data.value.toRange(nowTotalExperience)
                    LEVEL -> dropTotalLevel = data.value.toRange(nowTotalLevel)
                }
            }
            RANDOM -> {
                when (data.unit) {
                    EXPERIENCE -> dropTotalExperience = random(0, nowTotalExperience)
                    LEVEL -> dropTotalLevel = random(0, nowTotalLevel)
                }
            }
            ALL -> {
                when (data.unit) {
                    EXPERIENCE -> dropTotalExperience = nowTotalExperience
                    LEVEL -> dropTotalLevel = nowTotalLevel
                }
            }
            NONE -> {}
        }

        when (data.unit) {
            EXPERIENCE -> {
                val newTotalExperience = nowTotalExperience - dropTotalExperience
                player.totalExperience = newTotalExperience
                val newLevel = getIntLevelFromExp(newTotalExperience)
                val expForLevel = getExpFromLevel(newLevel)
                val expToNext = getExpToNext(newLevel)
                val progress = (newTotalExperience - expForLevel).toFloat() / expToNext
                player.level = newLevel
                player.exp = progress
            }
            LEVEL -> {
                val newLevel = nowTotalLevel - dropTotalLevel
                dropTotalExperience  = nowTotalExperience - getIntLevelFromExp(newLevel)
                player.level = newLevel
            }
        }

        when (data.mode) {
            DROP -> dropTotalExperience.dropExpAt(player.world, player.location)
            REMOVE -> {}
        }
    }

    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }
}