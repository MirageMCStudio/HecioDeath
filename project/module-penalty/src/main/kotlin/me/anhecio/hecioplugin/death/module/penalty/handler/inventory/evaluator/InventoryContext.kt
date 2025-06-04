package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator

import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator
 *
 * @author Anhecio
 * @since 2025/6/4 21:12
 */
class InventoryContext (
    val map: MutableMap<CompatSlot, ItemStack>,
    val player: Player
)