package me.anhecio.hecioplugin.death.module.javascript.hook.impl

import me.anhecio.hecioplugin.death.module.javascript.hook.NashornHooker
import me.anhecio.hecioplugin.death.nashorn.api.scripting.NashornScriptEngineFactory
import javax.script.ScriptEngine

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript.hook.impl
 *
 * @author Anhecio
 * @since 2025/6/1 09:09
 */
class NashornHookerImpl : NashornHooker() {
    override fun getNashornEngine(args: Array<String>, classLoader: ClassLoader): ScriptEngine {
        return NashornScriptEngineFactory().getScriptEngine(args, classLoader)
    }
}