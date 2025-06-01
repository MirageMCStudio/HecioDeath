package me.anhecio.hecioplugin.death.module.javascript

import java.io.File
import java.io.Reader
import javax.script.CompiledScript
import javax.script.ScriptContext
import javax.script.ScriptEngine

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript
 *
 * @author Anhecio
 * @since 2025/6/1 16:50
 */
class DefaultHecioDeathScriptExpansion : DefaultHecioDeathCompiledScript {
    /**
     * 构建JavaScript脚本扩展
     *
     * @property script js脚本文本
     * @constructor JavaScript脚本扩展
     */
    constructor(script: String) : super(script)

    override fun loadLib() {
        scriptEngine.eval(
            """
                const Bukkit = Packages.org.bukkit.Bukkit
                const Material = Packages.org.bukkit.Material
                const ItemStack = Packages.org.bukkit.inventory.ItemStack
                const HecioDeath = Package.me.anhecio.hecioplugin.death.module.bukkit.HecioDeathPlugin
                const pluginManager = Bukkit.getPluginManager()
                const scheduler = Bukkit.getScheduler()
                const plugin = pluginManager.getPlugin("HecioDeath")
            """.trimIndent()
        )
    }
}