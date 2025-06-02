package me.anhecio.hecioplugin.death.common

import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 18:14
 */
interface HecioDeathPlayerSlotController {

    /**
     * 解析槽位Id为CompatSlot
     */
    fun toCompatSlot(slotId: String, slotType: PlayerSlotType): CompatSlot

    /**
     * 获取指定玩家所有适配的槽位物品
     */
    fun getPlayerAllSlots(player: Player): Map<CompatSlot, ItemStack>

    /**
     * 获取玩家指定槽位的物品
     */
    fun getPlayerSlot(player: Player, slotId: CompatSlot): ItemStack?

    /**
     * 设置玩家指定槽位的物品
     */
    fun setPlayerSlot(player: Player, slotId: CompatSlot, target: ItemStack)
}