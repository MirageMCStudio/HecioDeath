package me.anhecio.hecioplugin.death.module.javascript.hook

import java.io.Reader
import javax.script.Compilable
import javax.script.CompiledScript
import javax.script.ScriptEngine

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript.hook
 *
 * @author Anhecio
 * @since 2025/6/1 09:09
 */
abstract class NashornHooker {
    /**
     * 获取一个新的Nashorn引擎
     *
     * @return 一个新的Nashorn引擎
     */
    fun getNashornEngine(): ScriptEngine {
        return getNashornEngine(arrayOf("--language=es6"))
    }

    /**
     * 获取一个共享上下文的Nashorn引擎(不要尝试在这个引擎中声明变量, 出现BUG自行思考)
     *
     * @return 一个新的Nashorn引擎
     */
    fun getGlobalEngine(): ScriptEngine {
        return getNashornEngine(arrayOf("--language=es6", "--global-per-engine"))
    }

    /**
     * 获取一个新的Nashorn引擎
     *
     * @param args 应用于引擎的参数
     * @return 一个新的Nashorn引擎
     */
    fun getNashornEngine(args: Array<String>): ScriptEngine {
        return getNashornEngine(args, this::class.java.classLoader)
    }

    /**
     * 获取一个新的Nashorn引擎
     *
     * @param args 应用于引擎的参数
     * @param classLoader 用于生成引擎的classLoader
     * @return 一个新的Nashorn引擎
     */
    abstract fun getNashornEngine(args: Array<String>, classLoader: ClassLoader): ScriptEngine


    /**
     * 编译一段js脚本, 返回已编译脚本对象
     *
     * @param engine 用于编译脚本的脚本引擎
     * @param string 待编译脚本文本
     * @return 已编译JS脚本
     */
    fun compile(engine: ScriptEngine, string: String): CompiledScript {
        return (engine as Compilable).compile(string)
    }

}