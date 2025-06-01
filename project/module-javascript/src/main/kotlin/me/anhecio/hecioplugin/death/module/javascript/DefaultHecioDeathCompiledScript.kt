package me.anhecio.hecioplugin.death.module.javascript

import me.anhecio.hecioplugin.death.module.javascript.DefaultHecioDeathScriptManager.nashornHooker

import javax.script.ScriptEngine

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript
 *
 * @author Anhecio
 * @since 2025/6/1 16:47
 */
open class DefaultHecioDeathCompiledScript {
    val handle: javax.script.CompiledScript

    /**
     * 获取该脚本对应的ScriptEngine
     */
    val scriptEngine: ScriptEngine = nashornHooker.getNashornEngine()

    /**
     * 编译js脚本并进行包装, 便于调用其中的指定函数
     *
     * @property script js脚本文本
     * @constructor 编译js脚本并进行包装
     */
    constructor(script: String) {
        loadLib()
        handle = nashornHooker.compile(scriptEngine, script)
        magicFunction()
    }

    /**
     * 加载JS前置库
     */
    open fun loadLib() {}

    /**
     * 此段代码用于解决js脚本的高并发调用问题, 只可意会不可言传
     */
    private fun magicFunction() {
        handle.eval()
        scriptEngine.eval(
            """
            function HecioDeathNumberOne() {}
            HecioDeathNumberOne.prototype = this
            function newObject() { return new HecioDeathNumberOne() }
        """
        )
    }
}