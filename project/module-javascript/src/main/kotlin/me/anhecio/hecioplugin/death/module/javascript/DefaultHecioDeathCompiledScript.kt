package me.anhecio.hecioplugin.death.module.javascript

import me.anhecio.hecioplugin.death.module.javascript.DefaultHecioDeathScriptManager.nashornHooker
import sun.misc.Signal.handle
import javax.script.Bindings
import javax.script.CompiledScript

import javax.script.ScriptEngine

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript
 *
 * @author Anhecio
 * @since 2025/6/1 16:47
 */
class DefaultHecioDeathCompiledScript(script: String) {

    /**
     * 当前脚本对应的 ScriptEngine
     */
    var scriptEngine: ScriptEngine = nashornHooker.getNashornEngine()

    /**
     * 编译后脚本
     */
    val compiledScript: CompiledScript = nashornHooker.compile(scriptEngine, script)

    init {
        loadLib()
    }

    /**
     * 加载脚本执行所需
     */
    private fun loadLib() {
        val bindings = scriptEngine.createBindings()
        bindings.putAll(
            mapOf(
                "Bukkit" to org.bukkit.Bukkit::class.java,
                "Arrays" to java.util.Arrays::class.java,
                "Material" to  org.bukkit.Material::class.java,
                "ItemStack" to org.bukkit.inventory.ItemStack::class.java,
                "plugin" to org.bukkit.Bukkit.getPluginManager().getPlugin("HecioDeath")!!::class.java,
                "scheduler" to org.bukkit.Bukkit.getScheduler()::class.java,
                "HecioDeath" to me.anhecio.hecioplugin.death.common.HecioDeath::class.java
            )
        )
        scriptEngine.setBindings(bindings,javax.script.ScriptContext.GLOBAL_SCOPE)
    }

    fun injectPrototypeWrapper() {
        scriptEngine.eval(
            """
            function HecioDeathNumberOne() {}
            HecioDeathNumberOne.prototype = this
            function newObject() { return new HecioDeathNumberOne() }
            """.trimIndent()
        )
    }

    /**
     * 执行脚本 绑定新Bindings
     */
    fun eval(bindings: Bindings): Any? {
        compiledScript.engine.setBindings(bindings, javax.script.ScriptContext.ENGINE_SCOPE)
        val result: Any? = compiledScript.eval()
        injectPrototypeWrapper()
        return result
    }

    /**
     * 执行脚本 使用原有Bindings
     */
    fun eval(): Any? {
        val result: Any? = compiledScript.eval()
        injectPrototypeWrapper()
        return result
    }
}