package me.anhecio.hecioplugin.death.common.compat.data

import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.compat.data
 *
 * @author Anhecio
 * @since 2025/5/31 18:16
 */
data class CompatSlot(
    val slotId: String,
    val slotType: PlayerSlotType
)