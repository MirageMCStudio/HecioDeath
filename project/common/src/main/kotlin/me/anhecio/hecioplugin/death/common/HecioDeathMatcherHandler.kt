package me.anhecio.hecioplugin.death.common

import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 16:15
 */
interface HecioDeathMatcherHandler {
    /**
     * 获取配置管理器
     */
    fun getConfigManager(): HecioDeathConfigService

    /**
     * 判断指定匹配配置文件中的所有节点
     */
    fun evaluateAllNodes(id: String, event: PlayerDeathEvent): List<Boolean>

    /**
     * 判断指定匹配配置文件中的指定节点
     */
    fun evaluateNode(id: String, match: String, event: PlayerDeathEvent): Boolean

    /**
     * 判断匹配配置与玩家是否匹配
     */
    fun matcher(event: PlayerDeathEvent, matcher: ConfigurationSection): Boolean
}