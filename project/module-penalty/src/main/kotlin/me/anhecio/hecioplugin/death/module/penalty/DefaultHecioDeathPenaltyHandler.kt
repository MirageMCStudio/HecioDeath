package me.anhecio.hecioplugin.death.module.penalty

import me.anhecio.hecioplugin.death.common.HecioDeathConfigService
import me.anhecio.hecioplugin.death.common.HecioDeathPenaltyHandler
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration

/**
 * HecioDeath
 * me.anhecio.hecioplugin.module.penalty
 *
 * 默认的死亡惩罚处理器
 * @author Anhecio
 * @since 2025/5/31 16:17
 */
class DefaultHecioDeathPenaltyHandler : HecioDeathPenaltyHandler {

    override fun getConfigManager(): HecioDeathConfigService = config

    override fun getParsedConfigManager(): List<Map.Entry<String, Configuration>> = parsedConfigs

    override fun getBindMatchers(penaltyId: String): ConfigurationSection? {

        val config = config.cache[penaltyId] ?: error("未知的惩罚器Id: '$penaltyId'")

        val options = config.getConfigurationSection(OPTIONS_NODE_PATH)
            ?: error("惩罚器 '$penaltyId' 中未找到配置节点: '$OPTIONS_NODE_PATH'")

        if (options.getBoolean(DEFAULT_NODE_PATH, false)) return null

        return options.getConfigurationSection(BIND_NODE_PATH)
            ?: error("惩罚器 '$penaltyId' 中未找到绑定匹配器配置: '$OPTIONS_NODE_PATH.$BIND_NODE_PATH'")

    }

    companion object {

        /** 主配置服务（惩罚器配置） */
        val config = HecioDeathConfigService("penalty") {
            updateParsedConfigs()
        }

        /** 常量节点名 */
        const val OPTIONS_NODE_PATH = "options"
        const val BIND_NODE_PATH = "bind-matcher"
        const val WEIGHT_NODE_PATH = "weight"
        const val DEFAULT_NODE_PATH = "default"
        const val ENABLE_NODE_PATH = "enable"

        /** 按权重排序后的缓存（默认空列表） */
        private var parsedConfigs: List<Map.Entry<String, Configuration>> = emptyList()

        /** 更新缓存 */
        fun updateParsedConfigs() {
            parsedConfigs = config.cache.entries.sortedByDescending { entry ->
                entry.value.getInt("$OPTIONS_NODE_PATH.$WEIGHT_NODE_PATH", 0)
            }.filter { it.value.getBoolean("$OPTIONS_NODE_PATH.$ENABLE_NODE_PATH", true) }
        }

        /** 注册接口 */
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathPenaltyHandler>(DefaultHecioDeathPenaltyHandler())
        }

        /** 加载配置并执行排序 */
        @Awake(LifeCycle.LOAD)
        fun loadConfigs() {
            config.load()
            updateParsedConfigs()
        }
    }
}
