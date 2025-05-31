package me.anhecio.hecioplugin.death.compat.slot.vanilla

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.compat.PlayerSlotCompat
import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.compat.slot.DefaultHecioDeathPlayerSlotController
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.isNotAir

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.slot.vanilla
 *
 * @author Anhecio
 * @since 2025/5/31 18:34
 */
object VanillaSlotCompat : PlayerSlotCompat {
    override fun getPlayerAllSlots(player: Player): Map<CompatSlot, ItemStack> {
        val slots = hashMapOf<CompatSlot, ItemStack>()
        player.inventory.withIndex()
            .filter { (_, itemStack) -> itemStack.isNotAir() }
            .forEach { (index, itemStack) ->
                slots.put(
                    DefaultHecioDeathPlayerSlotController().toCompatSlot(index.toString(), PlayerSlotType.Vanilla),
                    itemStack
                )
            }
        return slots
    }

    override fun getPlayerSlot(player: Player, slotId: CompatSlot): ItemStack? {
        return getPlayerAllSlots(player)[slotId]
    }

    override fun setPlayerSlot(player: Player, slotId: CompatSlot, item: ItemStack) {
        player.inventory.setItem(slotId.slotId.toInt(), item)
    }
}