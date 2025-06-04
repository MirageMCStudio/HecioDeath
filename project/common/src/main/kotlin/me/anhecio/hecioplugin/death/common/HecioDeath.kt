package me.anhecio.hecioplugin.death.common

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.scheduler.BukkitScheduler
import taboolib.platform.BukkitPlugin
import java.util.UUID

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 11:08
 */
object HecioDeath {
    private var api: HecioDeathAPI? = null

    /** 调度器实例 */
    val bukkitScheduler by lazy { Bukkit.getScheduler() }
    /** 插件实例 */
    val plugin by lazy { BukkitPlugin.getInstance() }

    /**
     * 获取开发者接口
     */
    fun api(): HecioDeathAPI {
        return api ?: error("HecioDeathAPI has not finished loading, or failed to load!")
    }

    /**
     * 注册开发者接口
     */
    fun register(api: HecioDeathAPI) {
        HecioDeath.api = api
    }
}