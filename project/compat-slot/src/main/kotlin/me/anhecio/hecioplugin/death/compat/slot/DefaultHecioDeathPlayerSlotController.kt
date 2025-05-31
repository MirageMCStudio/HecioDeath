package me.anhecio.hecioplugin.death.compat.slot

import me.anhecio.hecioplugin.death.common.HecioDeathPlayerSlotController
import me.anhecio.hecioplugin.death.common.HecioDeathSettings
import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.common.util.parseToSlotType
import me.anhecio.hecioplugin.death.compat.slot.arcartx.ArcartXSlotCompat
import me.anhecio.hecioplugin.death.compat.slot.dragoncore.DragonCoreSlotCompat
import me.anhecio.hecioplugin.death.compat.slot.germplugin.GermPluginSlotCompat
import me.anhecio.hecioplugin.death.compat.slot.vanilla.VanillaSlotCompat
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.compat.slot
 *
 * @author Anhecio
 * @since 2025/5/31 18:30
 */
class DefaultHecioDeathPlayerSlotController : HecioDeathPlayerSlotController {

    override val slots = mutableMapOf<CompatSlot, ItemStack>()

    override fun toCompatSlotId(slotId: String, slotType: PlayerSlotType): CompatSlot {
        return CompatSlot(slotId, slotType)
    }

    override fun getPlayerAllSlots(player: Player): Map<CompatSlot, ItemStack> {
        HecioDeathSettings.compatSlot.forEach { compat ->
            when (compat.parseToSlotType()) {
                PlayerSlotType.Vanilla -> slots.putAll(VanillaSlotCompat().getPlayerAllSlots(player))
                PlayerSlotType.ArcartX -> slots.putAll(ArcartXSlotCompat().getPlayerAllSlots(player))
                PlayerSlotType.DragonCore -> slots.putAll(DragonCoreSlotCompat().getPlayerAllSlots(player))
                PlayerSlotType.GermPlugin -> slots.putAll(GermPluginSlotCompat().getPlayerAllSlots(player))
            }
        }
        return slots
    }

    override fun getPlayerSlot(player: Player, slotId: CompatSlot): ItemStack? {
        return when (slotId.slotType) {
            PlayerSlotType.Vanilla -> VanillaSlotCompat().getPlayerSlot(player, slotId)
            PlayerSlotType.DragonCore -> DragonCoreSlotCompat().getPlayerSlot(player, slotId)
            PlayerSlotType.GermPlugin -> GermPluginSlotCompat().getPlayerSlot(player, slotId)
            PlayerSlotType.ArcartX -> ArcartXSlotCompat().getPlayerSlot(player, slotId)
        }
    }

    override fun setPlayerSlot(player: Player, slotId: CompatSlot, target: ItemStack) {
        when (slotId.slotType) {
            PlayerSlotType.Vanilla -> VanillaSlotCompat().setPlayerSlot(player,  slotId, target)
            PlayerSlotType.DragonCore -> DragonCoreSlotCompat().setPlayerSlot(player,  slotId, target)
            PlayerSlotType.GermPlugin -> GermPluginSlotCompat().setPlayerSlot(player,  slotId, target)
            PlayerSlotType.ArcartX -> ArcartXSlotCompat().setPlayerSlot(player,  slotId, target)
        }
    }

    companion object {
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathPlayerSlotController>(DefaultHecioDeathPlayerSlotController())
        }
    }

}