package me.anhecio.hecioplugin.death.module.javascript

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.HecioDeathConfigService
import me.anhecio.hecioplugin.death.common.util.getConfigSections
import me.anhecio.hecioplugin.death.module.javascript.hook.NashornHooker
import me.anhecio.hecioplugin.death.module.javascript.hook.impl.LegacyNashornHookerImpl
import me.anhecio.hecioplugin.death.module.javascript.hook.impl.NashornHookerImpl
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import javax.script.CompiledScript

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript
 *
 * @author Anhecio
 * @since 2025/6/1 16:40
 */
object DefaultHecioDeathScriptManager {
    /**
     * 获取所有已编译的js脚本文件及路径
     */
    val compiledScripts = HashMap<String, DefaultHecioDeathCompiledScript>()

    private fun check(clazz: String): Boolean {
        return try {
            Class.forName(clazz)
            true
        } catch (error: Throwable) {
            false
        }
    }

    val nashornHooker: NashornHooker =
        when {
            // jdk11 以下使用 jdk 自带 nashorn
            check("jdk.nashorn.api.scripting.NashornScriptEngineFactory") && ((System.getProperty("java.class.version")
                .toDoubleOrNull() ?: 0.0) < 55.0) -> LegacyNashornHookerImpl()
            // jdk11 以上使用 openjdk nashorn
            else -> NashornHookerImpl()
        }

    fun preheat() {
        // 获取匹配配置管理器
        val matcherManager = HecioDeath.api().getMatcher().getConfigManager()
        // 获取惩罚器配置
        val penaltyManager = HecioDeath.api().getPenalty().getConfigManager()
        listOf(
            penaltyManager,
            matcherManager
        ).forEach { processConfig(it) }
    }

    fun processConfig(config: HecioDeathConfigService) {
        config.cache.forEach { (id, configuration) ->
            configuration.getConfigSections()
                .filter { it.getString("type").equals("javascript", ignoreCase = true) }
                .forEach { section ->
                    val path = id + section.name
                    val script = section.getString("run") ?: error("配置 '$path' 不存在 'run' 节点")
                    compiledScripts[path] = DefaultHecioDeathCompiledScript(script)
                }
        }
    }
}