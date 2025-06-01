package me.anhecio.hecioplugin.death.module.javascript

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
    override fun compile(script: String): CompiledScript? {
        TODO("Not yet implemented")
    }

    override fun eval(script: String, context: ScriptContext?): Any? {
        TODO("Not yet implemented")
    }

    override fun evalCompiled(script: CompiledScript, context: ScriptContext?): Any? {
        TODO("Not yet implemented")
    }


    companion object {
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathJavaScriptHandler>(DefaultHecioDeathJavaScripHandler())
        }
    }
}