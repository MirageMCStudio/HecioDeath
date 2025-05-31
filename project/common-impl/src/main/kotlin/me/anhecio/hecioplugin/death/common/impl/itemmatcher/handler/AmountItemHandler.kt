package me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.api.ItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.NumberOperator
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils.ParserUtils
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 18:01
 */
class AmountItemHandler : ItemHandler {

    override fun check(item: ItemStack, value: String): Boolean {
        val condition = ParserUtils.parseNumberCondition(value)
        return when (condition.operator) {
            NumberOperator.GREATER_EQUAL -> item.amount >= condition.value
            NumberOperator.LESS_EQUAL -> item.amount <= condition.value
            NumberOperator.GREATER -> item.amount > condition.value
            NumberOperator.LESS -> item.amount < condition.value
            else -> item.amount == condition.value
        }
    }
}