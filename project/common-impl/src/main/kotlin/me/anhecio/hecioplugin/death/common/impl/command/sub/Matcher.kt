package me.anhecio.hecioplugin.death.common.impl.command.sub

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.util.getConfigSections
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.command.suggest
import taboolib.common.platform.command.suggestPlayers
import taboolib.platform.util.sendLang

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.command.sub
 *
 * @author Anhecio
 * @since 2025/6/2 09:39
 */
val matcherSubCommand = subCommand {
    dynamic("matcherId") {
        suggest { HecioDeath.api().getMatcher().getConfigManager().cache.keys.toList() }
        player("player", optional = true) {
            suggestPlayers()
            dynamic("node") {
                suggestion<CommandSender> { _, context ->
                    HecioDeath.api().getMatcher().getConfigManager().cache[context["matcherId"]]!!
                        .getConfigSections().map { section -> section.name }
                }
                execute<CommandSender> { sender, context, _ ->
                    val result = HecioDeath.api().getMatcher()
                        .evaluateNode(context["matcherId"], context["node"], mapOf("player" to (context.player("player").cast<Player>())))
                    sender.sendLang("Command-Matcher-Node",context["matcherId"], context["node"], result)
                }
            }
            execute<CommandSender> { sender, context, _ ->
                val result = HecioDeath.api().getMatcher()
                    .evaluateAllNodes(context["matcherId"], mapOf("player" to (context.player("player").cast<Player>())))
                    .all { it }
                sender.sendLang("Command-Matcher-All-Node", context["matcherId"], result)
            }
        }
        execute<Player> { sender, context, _ ->
            val result = HecioDeath.api().getMatcher()
                .evaluateAllNodes(context["matcherId"], mapOf("player" to sender))
                .all { it }
            sender.sendLang("Command-Matcher-All-Node", context["matcherId"], result)
        }
    }
}