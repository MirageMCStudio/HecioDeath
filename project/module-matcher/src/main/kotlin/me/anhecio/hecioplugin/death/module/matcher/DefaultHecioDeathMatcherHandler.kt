package me.anhecio.hecioplugin.death.module.matcher

import me.anhecio.hecioplugin.death.common.HecioDeathConfigService
import me.anhecio.hecioplugin.death.common.HecioDeathMatcherHandler
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.event.entity.PlayerDeathEvent
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

    override fun evaluateAllNodes(
        id: String,
        event: PlayerDeathEvent,
    ): List<Boolean> {
        val section = config.cache[id] ?: error("No matcher group found for id: '$id'")
        return section.getKeys(false).map { match ->
            evaluateNode(id, match, event)
        }
    }

    override fun evaluateNode(
        id: String,
        match: String,
        event: PlayerDeathEvent,
    ): Boolean {
        val section = config.cache[id] ?: error("No matcher group found for id: '$id'")
        val subSection = section.getConfigurationSection(match)
            ?: error("Matcher '$match' not found in group '$id'")
        val type = subSection.getString("type")
            ?: error("Missing 'type' in matcher '$id::$match'")
        val handler = MatcherRegistry.get(type)
            ?: error("No matcher handler registered for type: '$type'")
        return handler.match(event, subSection)
    }

    override fun matcher(
        event: PlayerDeathEvent,
        matcher: ConfigurationSection,
    ): Boolean {
        // all
        matcher.getStringList("all").takeIf { it.isNotEmpty() }?.let { entries ->
            if (!evaluate(event, entries) { it.not() }) return false
        }

        // any
        matcher.getStringList("any").takeIf { it.isNotEmpty() }?.let { entries ->
            if (!evaluate(event, entries) { it }) return false
        }

        // none
        matcher.getStringList("none").takeIf { it.isNotEmpty() }?.let { entries ->
            if (evaluate(event, entries) { it }) return false
        }

        return true
    }

    private fun evaluate(event: PlayerDeathEvent, entries: List<String>, predicate: (Boolean) -> Boolean): Boolean {
        return predicate(entries.any { entry ->
            val parts = entry.split("::")
            require(parts.size == 2) { "Invalid matcher reference format: '$entry'. Expected 'id::match'" }

            val (id, match) = parts

            val matchResults = if (match == "*") {
                evaluateAllNodes(id, event)
            } else {
                listOf(evaluateNode(id, match, event))
            }

            matchResults.all { it }
        })
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