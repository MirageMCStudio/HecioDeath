package me.anhecio.hecioplugin.death.module.matcher

import me.anhecio.hecioplugin.death.common.HecioDeathConfigService
import me.anhecio.hecioplugin.death.common.HecioDeathMatcherHandler
import me.anhecio.hecioplugin.death.common.util.debug
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import sun.audio.AudioPlayer.player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.matcher
 *
 * @author Anhecio
 * @since 2025/5/31 16:16
 */
class DefaultHecioDeathMatcherHandler : HecioDeathMatcherHandler {
    override fun getConfigManager(): HecioDeathConfigService = config

    override fun evaluateAllNodes(id: String, context: Map<String, Any?>): List<Boolean> {
        val section = config.cache[id] ?: error("未知的匹配器Id: $id")
        return section.getKeys(false).map { match ->
            evaluateNode(id, match, context)
        }
    }

    override fun evaluateNode(id: String, match: String, context: Map<String, Any?>): Boolean {
        val section = config.cache[id] ?: error("未知的匹配器Id: $id")
        val subSection = section.getConfigurationSection(match)
            ?: error("无法在匹配器 '$id' 中找到配置节点: $match")
        val type = subSection.getString("type")
            ?: error("'$id::$match' 中未设置匹配类型.")
        val handler = MatcherRegistry.get(type)
            ?: error("'$id::$match' 使用了未知的匹配类型: $type")
        val result = handler.match(context, subSection)
        debug {
            if (result) {
                debug("|- 成功匹配匹配器: ${id + "::" + subSection.name}.")
            } else {
                debug("|- 无法匹配匹配器: ${id + "::" + subSection.name}.")
            }
        }

        return result
    }

    override fun matcher(context: Map<String, Any?>, matcher: ConfigurationSection): Boolean {
        // all: 全部为 true，才返回 true
        if (!evaluate(context, matcher.getStringList("all")) { results -> results.all { it } }) {
            return false
        }

        // any: 只要有一个为 true，就返回 true
        if (!evaluate(context, matcher.getStringList("any")) { results -> results.any { it } }) {
            return false
        }

        // none: 所有都为 false，才返回 true
        if (!evaluate(context, matcher.getStringList("none")) { results -> results.none { it } }) {
            return false
        }

        return true
    }


    private fun evaluate(
        context: Map<String, Any?>,
        entries: List<String>,
        decision: (List<Boolean>) -> Boolean
    ): Boolean {
        if (entries.isEmpty()) return true

        val results = entries.map { entry ->
            val parts = entry.split("::")
            require(parts.size == 2) { "匹配器路径 '$entry' 不合法." }

            val (id, match) = parts

            val matchResults = if (match == "*") {
                evaluateAllNodes(id, context)
            } else {
                listOf(evaluateNode(id, match, context))
            }

            matchResults.all { it }
        }

        return decision(results)
    }



    companion object {
        val config = HecioDeathConfigService("matcher")

        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathMatcherHandler>(DefaultHecioDeathMatcherHandler())
        }

        @Awake(LifeCycle.LOAD)
        fun loadConfigs() = config.load()
    }
}