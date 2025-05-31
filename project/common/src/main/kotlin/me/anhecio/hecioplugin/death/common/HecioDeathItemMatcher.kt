package me.anhecio.hecioplugin.death.common

import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 17:48
 */
interface HecioDeathItemMatcher {
    /**
     * 对指定物品进行指定匹配
     */
    fun match(itemStack: ItemStack, match: String): Boolean
    /**
     * 判断物品列表是否存在匹配的物品
     */
    fun contains(list: List<ItemStack>, match: String): Boolean
}