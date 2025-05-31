package me.anhecio.hecioplugin.death.module.bukkit

import me.anhecio.hecioplugin.death.common.impl.DefaultHecioDeathBooster
import taboolib.common.LifeCycle
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.registerLifeCycleTask
import taboolib.module.lang.sendLang

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.bukkit
 *
 * @author Lumira311
 * @since 2025/5/31 11:04
 */
object HecioDeathPlugin : Plugin() {
    init {
        registerLifeCycleTask(LifeCycle.INIT) {
            try {
                DefaultHecioDeathBooster.startup()
            } catch (ex: Throwable) {
                ex.printStackTrace()
                disablePlugin()
            }
        }
    }
}