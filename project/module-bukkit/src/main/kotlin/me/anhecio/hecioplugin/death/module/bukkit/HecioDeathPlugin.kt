package me.anhecio.hecioplugin.death.module.bukkit

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.impl.DefaultHecioDeathBooster
import me.anhecio.hecioplugin.death.common.util.debug
import org.bukkit.Bukkit
import taboolib.common.LifeCycle
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.common.platform.function.disablePlugin
import taboolib.common.platform.function.pluginVersion
import taboolib.common.platform.function.registerLifeCycleTask
import taboolib.module.lang.sendLang
import taboolib.module.lang.sendMessage

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.bukkit
 *
 * @author Anhecio
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

    override fun onLoad() {
        console().sendMessage("")
        console().sendLang("Plugin-Loading", Bukkit.getServer().version)
        console().sendMessage("")
    }

    override fun onEnable() {
        val matcherCache = HecioDeath.api().getMatcher().getConfigManager().cache
        val penaltyCache = HecioDeath.api().getPenalty().getParsedConfigManager()
        console().sendLang("Plugin-Enabled", matcherCache.size, penaltyCache.size, pluginVersion)
        debug("Debug 模式已开启.")
    }

    override fun onDisable() {
        console().sendLang("Plugin-Disabled")
    }
}