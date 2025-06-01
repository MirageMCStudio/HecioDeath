package me.anhecio.hecioplugin.death.module.penalty

import me.anhecio.hecioplugin.death.common.HecioDeathConfigService
import me.anhecio.hecioplugin.death.common.HecioDeathPenaltyHandler
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.module.penalty
 *
 * @author Anhecio
 * @since 2025/5/31 16:17
 */
class DefaultHecioDeathPenaltyHandler : HecioDeathPenaltyHandler {
    override fun getConfigManager(): HecioDeathConfigService = config

    override fun getBindMatchers(penaltyId: String): ConfigurationSection {
        val config = config.cache[penaltyId] ?: error("未知的惩罚器Id: '$penaltyId'")
        if (!config.contains(BIND_NODE_PATH)) error("惩罚器 '$penaltyId' 中未找到绑定匹配器配置节点: '$BIND_NODE_PATH'")
        return config.getConfigurationSection(BIND_NODE_PATH)!!
    }

    companion object {
        val config = HecioDeathConfigService("penalty")

        const val BIND_NODE_PATH = "bind-matcher"

        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathPenaltyHandler>(DefaultHecioDeathPenaltyHandler())
        }

        @Awake(LifeCycle.LOAD)
        fun loadConfigs() = config.load()
    }
}