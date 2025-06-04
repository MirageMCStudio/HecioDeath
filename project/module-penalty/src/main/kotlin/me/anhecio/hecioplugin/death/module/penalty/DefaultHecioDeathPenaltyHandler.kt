package me.anhecio.hecioplugin.death.module.penalty

import me.anhecio.hecioplugin.death.common.BIND_NODE_PATH
import me.anhecio.hecioplugin.death.common.DEFAULT_NODE_PATH
import me.anhecio.hecioplugin.death.common.ENABLE_NODE_PATH
import me.anhecio.hecioplugin.death.common.HecioDeathConfigService
import me.anhecio.hecioplugin.death.common.HecioDeathPenaltyHandler
import me.anhecio.hecioplugin.death.common.OPTIONS_NODE_PATH
import me.anhecio.hecioplugin.death.common.WEIGHT_NODE_PATH
import me.anhecio.hecioplugin.death.common.util.debug
import me.anhecio.hecioplugin.death.common.util.getConfigSections
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.util.asMap

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

    override fun evaluateAllNodes(id: String, context: Map<String, Any?>) {
        val section = parsedConfigs.associate { it.toPair() }[id] ?: error("未知的惩罚器Id: $id")
        section.getKeys(false).forEach { penalty ->
            if (penalty == OPTIONS_NODE_PATH) return@forEach
            evaluateNode(id, penalty, context)
        }
    }

    override fun evaluateNode(id: String, penalty: String, context: Map<String, Any?>) {
        val section = parsedConfigs.associate { it.toPair() }[id] ?: error("未知的惩罚器Id: $id")
        val subSection = section.getConfigurationSection(penalty)
            ?: error("无法在惩罚器 '$id' 中找到配置节点: $penalty")
        val type = subSection.getString("type")
            ?: error("'$id::$penalty' 中未设置匹配类型.")
        val handler = PenaltyRegistry.get(type)
            ?: error("'$id::$penalty' 使用了未知的惩罚类型: $type")
        handler.penalty(context, subSection)
        debug("|- 成功执行惩罚器: ${id + "::" + subSection.name}.")
    }

    override fun getBindMatchers(penaltyId: String): ConfigurationSection? {

        val config = config.cache[penaltyId] ?: error("未知的惩罚器Id: '$penaltyId'")

        val options = config.getConfigurationSection(OPTIONS_NODE_PATH)
            ?: error("惩罚器 '$penaltyId' 中未找到配置节点: '$OPTIONS_NODE_PATH'")

        if (options.getBoolean(DEFAULT_NODE_PATH, false)) return null

        return options.getConfigurationSection(BIND_NODE_PATH)
            ?: error("惩罚器 '$penaltyId' 中未找到绑定匹配器配置: '$OPTIONS_NODE_PATH.$BIND_NODE_PATH'")

    }

    override fun penalty(context: Map<String, Any?>, penaltyId: String) = evaluateAllNodes(penaltyId, context)


    companion object {

        /** 主配置服务（惩罚器配置） */
        val config = HecioDeathConfigService("penalty") {
            updateParsedConfigs()
        }

        /** 缓存 */
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
