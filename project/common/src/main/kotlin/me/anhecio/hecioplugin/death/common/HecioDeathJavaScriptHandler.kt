package me.anhecio.hecioplugin.death.common

import javax.script.CompiledScript
import javax.script.ScriptContext

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 19:57
 */
interface HecioDeathJavaScriptHandler {

    /**
     * 执行脚本
     */
    fun eval(script: String, map: Map<String, Any?> = emptyMap()): Any?

    /**
     * 执行指定Id的已编译脚本
     */
    fun run(id: String, map: Map<String, Any?> = emptyMap()): Any?

    /**
     * 预热脚本
     */
    fun preheat()
}