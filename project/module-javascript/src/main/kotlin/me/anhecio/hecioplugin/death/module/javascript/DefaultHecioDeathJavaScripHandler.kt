package me.anhecio.hecioplugin.death.module.javascript

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.HecioDeathJavaScriptHandler
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import javax.script.CompiledScript
import javax.script.ScriptContext

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript
 *
 * @author Anhecio
 * @since 2025/5/31 20:01
 */
class DefaultHecioDeathJavaScripHandler : HecioDeathJavaScriptHandler {

    /**
     * 获取一个共享上下文的Nashorn引擎
     */
    val globalEngine by lazy { DefaultHecioDeathScriptManager.nashornHooker.getGlobalEngine() }

    override fun eval(script: String, map: Map<String, Any?>): Any? {
        val compiled = DefaultHecioDeathCompiledScript(script)
        val bindings = globalEngine.getBindings(ScriptContext.ENGINE_SCOPE)
        bindings.putAll(map)
        return  compiled.eval()
    }

    override fun run(id: String, map: Map<String, Any?>): Any? {
        val compiled = DefaultHecioDeathScriptManager.compiledScripts[id] ?: return null
        val bindings = compiled.scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE)
        bindings.putAll(map)
        return compiled.eval()
    }

    override fun preheat() = DefaultHecioDeathScriptManager.preheat()

    companion object {
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathJavaScriptHandler>(DefaultHecioDeathJavaScripHandler())
        }
    }
}