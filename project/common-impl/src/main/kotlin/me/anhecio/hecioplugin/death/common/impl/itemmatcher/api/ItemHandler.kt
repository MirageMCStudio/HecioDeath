package me.anhecio.hecioplugin.death.common.impl.itemmatcher.api

import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.api
 *
 * @author Anhecio
 * @since 2025/5/31 17:57
 */
interface ItemHandler {
    fun check(item: ItemStack, value: String): Boolean
}