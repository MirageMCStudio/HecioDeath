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
     * 编译脚本
     */
    fun compile(script: String): CompiledScript?

    /**
     * 执行脚本
     */
    fun eval(script: String, context: ScriptContext? = null): Any?

    /**
     * 执行已编译的脚本
     */
    fun evalCompiled(script: CompiledScript, context: ScriptContext? = null): Any?
}