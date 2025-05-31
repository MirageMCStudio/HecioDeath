package me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.api.ItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.StringOperation
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils.ParserUtils
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 18:05
 */
class MaterialItemHandler : ItemHandler {
    override fun check(item: ItemStack, value: String): Boolean {
        val materialName = item.type.name
        val condition = ParserUtils.parseStringCondition(value)
        return when (condition.operation) {
            StringOperation.EXACT -> condition.values.any { materialName.equals(it, true) }
            StringOperation.CONTAINS -> condition.values.any { materialName.contains(it, true) }
            StringOperation.REGEX -> condition.values.any {
                Regex(it, RegexOption.IGNORE_CASE).containsMatchIn(materialName)
            }
            StringOperation.STARTS_WITH -> condition.values.any { materialName.startsWith(it, true) }
            StringOperation.ENDS_WITH -> condition.values.any { materialName.endsWith(it, true) }
        }
    }
}