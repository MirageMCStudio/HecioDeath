package me.anhecio.hecioplugin.death.common

import taboolib.library.configuration.ConfigurationSection

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
     * 获取绑定匹配配置
     */
    fun getBindMatchers(penaltyId: String): ConfigurationSection
}