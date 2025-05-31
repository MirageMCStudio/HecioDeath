package me.anhecio.hecioplugin.death.common.impl.itemmatcher

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.api.ItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler.AmountItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler.EnchantItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler.LoreItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler.MaterialItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler.NameItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.handler.NbtItemHandler
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils.ParserUtils
import org.bukkit.inventory.ItemStack
import taboolib.module.chat.colored
import java.util.concurrent.ConcurrentHashMap

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher
 *
 * @author Anhecio
 * @since 2025/5/31 17:54
 */
class ItemMatcher {
    private val handlers = ConcurrentHashMap<String, ItemHandler>()

    fun registerHandler(name: String, handler: ItemHandler) {
        handlers[name] = handler
    }

    fun unregisterHandler(name: String) {
        handlers.remove(name)
    }

    init {
        registerHandler("name", NameItemHandler())
        registerHandler("lore", LoreItemHandler())
        registerHandler("nbt", NbtItemHandler())
        registerHandler("enchant", EnchantItemHandler())
        registerHandler("material", MaterialItemHandler())
        registerHandler("amount", AmountItemHandler())
    }

    fun match(itemStack: ItemStack, match: String): Boolean {
        return ParserUtils.splitConditions(match).all { condition ->
            val (key, value) = ParserUtils.parseKeyValue(condition) ?: return@all false
            handlers[key]?.check(itemStack, value.colored()) ?: false
        }
    }

    fun matchTry(itemStack: ItemStack, match: String): Boolean {
        return try {
            match(itemStack, match)
        } catch (e: Exception) {
            false
        }
    }
}