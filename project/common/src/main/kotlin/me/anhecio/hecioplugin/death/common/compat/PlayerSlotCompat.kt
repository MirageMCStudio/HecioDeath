package me.anhecio.hecioplugin.death.common.compat

import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.compat
 *
 * @author Anhecio
 * @since 2025/5/31 18:15
 */
interface PlayerSlotCompat {

    /**
     * 获取指定玩家的所有槽位物品
     *
     * @param player 玩家
     * @return 所有槽位物品以及槽位id
     */
    fun getPlayerAllSlots(player: Player): Map<CompatSlot, ItemStack>

    /**
     * 获取指定玩家指定槽位的物品
     *
     * @param player 玩家
     * @param slotId 槽位ID
     * @return 物品
     */
    fun getPlayerSlot(player: Player, slotId: CompatSlot): ItemStack?

    /**
     * 设置指定玩家的指定槽位的物品
     */
    fun setPlayerSlot(player: Player, slotId: CompatSlot, item: ItemStack)
}