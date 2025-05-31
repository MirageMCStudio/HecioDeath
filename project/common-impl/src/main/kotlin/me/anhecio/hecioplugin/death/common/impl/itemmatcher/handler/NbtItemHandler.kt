package me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.api.ItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils.ParserUtils
import org.bukkit.inventory.ItemStack
import taboolib.module.nms.getItemTag

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 18:06
 */
class NbtItemHandler : ItemHandler {
    override fun check(item: ItemStack, value: String): Boolean {
        if (!value.startsWith("{") || !value.endsWith("}")) return false
        val nbtData = ParserUtils.parseNbt(value.removeSurrounding("{", "}")) ?: return false
        val tag = item.getItemTag()
        return nbtData.all { (path, expected) ->
            val actual = tag.getDeep(path) ?: return@all false
            when (expected) {
                is String -> actual.asString() == expected
                is Number -> actual.asDouble() == expected.toDouble()
                is Boolean -> actual.asString() == expected.toString()
                else -> false
            }
        }
    }
}