package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.data

import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory.data
 *
 * @author Anhecio
 * @since 2025/6/4 17:26
 */
data class InventoryData(
    val executor : ConfigurationSection,
    val target: Target? = null,
    val filter : List<String>? = executor.getStringList("filter")
);