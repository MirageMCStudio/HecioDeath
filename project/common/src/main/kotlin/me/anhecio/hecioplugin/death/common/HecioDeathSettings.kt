package me.anhecio.hecioplugin.death.common

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigNode
import taboolib.module.configuration.Configuration

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Lumira311
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
    @ConfigNode("options.territory")
    var territory = "residence"

    /**
     * 启用的适配槽位类型
     */
    @ConfigNode("options.compat-slot")
    var compatSlot = emptyList<String>()


}