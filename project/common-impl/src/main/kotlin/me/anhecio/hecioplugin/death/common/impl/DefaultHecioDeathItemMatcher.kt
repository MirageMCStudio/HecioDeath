package me.anhecio.hecioplugin.death.common.impl

import me.anhecio.hecioplugin.death.common.HecioDeathItemMatcher
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.ItemMatcher
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl
 *
 * @author Anhecio
 * @since 2025/5/31 17:49
 */
class DefaultHecioDeathItemMatcher : HecioDeathItemMatcher {
    override fun match(itemStack: ItemStack, match: String): Boolean = ItemMatcher().matchTry(itemStack, match)

    override fun contains(list: List<ItemStack>, match: String): Boolean = list.any { match(it, match) }

    companion object {
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathItemMatcher>(DefaultHecioDeathItemMatcher())
        }
    }
}