package me.anhecio.hecioplugin.death.common

import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 11:25
 */
@ConfigNode(bind = "setting.yml")
object HecioDeathSettings {

    @Config("setting.yml")
    lateinit var setting: Configuration
        private set

    /**
     * 调试模式
     */
    @ConfigNode("options.debug")
    var debug = false

    /**
     * 插件兼容的领地插件
     */
    @ConfigNode("options.compat-territory")
    var compatTerritory = emptyList<String>()

    /**
     * 启用的适配槽位类型
     */
    @ConfigNode("options.compat-slot")
    var compatSlot = emptyList<String>()

    /**
     * 当玩家没有匹配的配置启用的默认配置
     */
    @ConfigNode("options.penalty-default")
    var default = ""

}

/** 惩罚配置节点 */
const val OPTIONS_NODE_PATH = "options"
/** 惩罚配置节点下的匹配器绑定节点 */
const val BIND_NODE_PATH = "bind-matcher"
/** 惩罚配置节点下的权重节点 */
const val WEIGHT_NODE_PATH = "weight"
/** 惩罚配置节点下的惩罚模式节点(是否为默认惩罚器) */
const val DEFAULT_NODE_PATH = "default"
/** 惩罚配置节点下的启用状态节点 */
const val ENABLE_NODE_PATH = "enable"
