package me.anhecio.hecioplugin.death.common

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 16:15
 */
interface HecioDeathPenaltyHandler {
    /**
     * 获取配置管理器
     */
    fun getConfigManager(): HecioDeathConfigService

    /**
     * 获取根据配置解析后的配置
     */
    fun getParsedConfigManager(): List<Map.Entry<String, Configuration>>

    /**
     * 执行指定惩罚配置文件中的所有节点
     */
    fun evaluateAllNodes(id: String, context: Map<String, Any?>)

    /**
     * 执行指定惩罚配置文件中的指定节点
     */
    fun evaluateNode(id: String, penalty: String, context: Map<String, Any?>)

    /**
     * 获取绑定匹配配置
     */
    fun getBindMatchers(penaltyId: String): ConfigurationSection?

    /**
     * 执行指定配置
     */
    fun penalty(context: Map<String, Any?>, penaltyId: String)
}