package me.anhecio.hecioplugin.death.module.javascript.hook.impl

import jdk.nashorn.api.scripting.NashornScriptEngineFactory
import me.anhecio.hecioplugin.death.module.javascript.hook.NashornHooker
import javax.script.ScriptEngine

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript.hook.impl
 *
 * @author Anhecio
 * @since 2025/6/1 09:09
 */
class LegacyNashornHookerImpl : NashornHooker() {
    override fun getNashornEngine(args: Array<String>, classLoader: ClassLoader): ScriptEngine {
        return NashornScriptEngineFactory().getScriptEngine(args, classLoader)
    }
}