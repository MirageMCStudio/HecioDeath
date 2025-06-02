package me.anhecio.hecioplugin.death.common.impl

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.HecioDeathSettings
import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.common.util.parseToSlotType
import me.anhecio.hecioplugin.death.common.util.parseToTerritoryType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Platform
import taboolib.common.platform.function.pluginVersion
import taboolib.module.metrics.Metrics
import taboolib.module.metrics.charts.AdvancedPie
import taboolib.module.metrics.charts.SingleLineChart

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl
 *
 * @author Anhecio
 * @since 2025/6/1 20:39
 */
object PluginMetrics {
    lateinit var metrics: Metrics

    @Awake(LifeCycle.ACTIVE)
    private fun init() {
        metrics = Metrics(25518, pluginVersion, Platform.BUKKIT)
        // matcher
        metrics.addCustomChart(SingleLineChart("matcher") {
            HecioDeath.api().getMatcher().getConfigManager().cache.size
        })
        // penalty
        metrics.addCustomChart(SingleLineChart("penalty") {
            HecioDeath.api().getPenalty().getConfigManager().cache.size
        })
        // slot
        metrics.addCustomChart(AdvancedPie("slot") {
            val map = mutableMapOf<String, Int>()
            HecioDeathSettings.compatSlot.forEach {
                try {
                    map.put(it.parseToSlotType().toString(), 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            map
        })
        // territory
        metrics.addCustomChart(AdvancedPie("territory") {
            val map = mutableMapOf<String, Int>()
            HecioDeathSettings.compatTerritory.forEach {
                try {
                    map.put(it.parseToTerritoryType().toString(), 1)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            map
        })
    }
}