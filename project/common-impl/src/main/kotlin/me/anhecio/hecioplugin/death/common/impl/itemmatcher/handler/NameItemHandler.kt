package me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.api.ItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.CompoundType
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.MatchCondition
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.StringOperation
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils.ParserUtils
import org.bukkit.inventory.ItemStack
import taboolib.module.chat.uncolored

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 18:05
 */
class NameItemHandler : ItemHandler {
    override fun check(item: ItemStack, value: String): Boolean {
        val meta = item.itemMeta ?: return false
        if (!meta.hasDisplayName()) {
            return false
        }
        val displayName = meta.displayName
        return when (val condition = ParserUtils.parseListCondition(value)) {
            is MatchCondition.StringCondition -> {
                checkStringCondition(displayName, condition)
            }
            is MatchCondition.CompoundCondition -> {
                when (condition.type) {
                    CompoundType.ANY -> condition.conditions.any { checkStringCondition(displayName, it as MatchCondition.StringCondition) }
                    CompoundType.ALL -> condition.conditions.all { checkStringCondition(displayName, it as MatchCondition.StringCondition) }
                    CompoundType.NONE -> condition.conditions.none { checkStringCondition(displayName, it as MatchCondition.StringCondition) }
                }
            }
            else -> false
        }
    }

    private fun checkStringCondition(string: String, condition: MatchCondition.StringCondition): Boolean {
        var displayName = string
        if (condition.modifiers.containsAll(listOf("uncolored", "uc"))) {
            displayName = displayName.uncolored()
        }
        if (condition.modifiers.containsAll(listOf("lowercase", "lc"))) {
            displayName = displayName.lowercase()
        }
        return when (condition.operation) {
            StringOperation.EXACT -> displayName == condition.values.first()
            StringOperation.CONTAINS -> condition.values.any { displayName.contains(it, true) }
            StringOperation.REGEX -> condition.values.any { Regex(it, RegexOption.IGNORE_CASE).containsMatchIn(displayName) }
            StringOperation.STARTS_WITH -> condition.values.any { displayName.startsWith(it, true) }
            StringOperation.ENDS_WITH -> condition.values.any { displayName.endsWith(it, true) }
        }
    }
}