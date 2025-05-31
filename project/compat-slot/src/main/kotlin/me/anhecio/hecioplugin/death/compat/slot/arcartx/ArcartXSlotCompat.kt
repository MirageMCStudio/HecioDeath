package me.anhecio.hecioplugin.death.compat.slot.arcartx

import me.anhecio.hecioplugin.death.common.compat.PlayerSlotCompat
import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.slot.arcartx
 *
 * @author Anhecio
 * @since 2025/5/31 18:37
 */
class ArcartXSlotCompat : PlayerSlotCompat {
    override fun getPlayerAllSlots(player: Player): Map<CompatSlot, ItemStack> {
        TODO("Not yet implemented")
    }

    override fun getPlayerSlot(
        player: Player,
        slotId: CompatSlot
    ): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun setPlayerSlot(
        player: Player,
        slotId: CompatSlot,
        item: ItemStack
    ) {
        TODO("Not yet implemented")
    }
}