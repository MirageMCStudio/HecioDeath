package me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.api.ItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.CompoundType
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.MatchCondition
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.NumberOperator
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils.ParserUtils
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 18:01
 */
class EnchantItemHandler : ItemHandler {
    override fun check(item: ItemStack, value: String): Boolean {
        return when (val condition = ParserUtils.parseNumberListCondition(value)) {
            is MatchCondition.CompoundCondition -> checkCompoundCondition(item, condition)
            is MatchCondition.NumberCondition -> checkCondition(item, condition)
            else -> false
        }
    }

    private fun checkCompoundCondition(item: ItemStack, condition: MatchCondition.CompoundCondition): Boolean {
        return when (condition.type) {
            CompoundType.ANY -> condition.conditions.any { checkCondition(item, it) }
            CompoundType.ALL -> condition.conditions.all { checkCondition(item, it) }
            CompoundType.NONE -> condition.conditions.none { checkCondition(item, it) }
        }
    }

    private fun checkCondition(item: ItemStack, condition: MatchCondition): Boolean {
        return when (condition) {
            is MatchCondition.NumberCondition -> {
                val enchant = Enchantment.getByName(condition.tag) ?: return false
                val actualLevel = item.getEnchantmentLevel(enchant)
                when (condition.operator) {
                    NumberOperator.GREATER_EQUAL -> actualLevel >= condition.value
                    NumberOperator.LESS_EQUAL -> actualLevel <= condition.value
                    NumberOperator.GREATER -> actualLevel > condition.value
                    NumberOperator.LESS -> actualLevel < condition.value
                    else -> actualLevel == condition.value
                }
            }
            else -> false
        }
    }
}