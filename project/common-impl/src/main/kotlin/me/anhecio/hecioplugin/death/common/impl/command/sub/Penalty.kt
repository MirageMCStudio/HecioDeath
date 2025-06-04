package me.anhecio.hecioplugin.death.common.impl.command.sub

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.OPTIONS_NODE_PATH
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
 * @since 2025/6/3 23:14
 */
val penaltySubCommand = subCommand {
    dynamic("penaltyId") {
        suggest { HecioDeath.api().getPenalty().getParsedConfigManager().associate { it.toPair() }.keys.toList() }
        player("player") {
            suggestPlayers()
            dynamic("node") {
                suggestion<CommandSender> { _, context ->
                    HecioDeath.api().getPenalty().getParsedConfigManager().associate { it.toPair() }[context["penaltyId"]]!!
                        .getConfigSections().map { section -> section.name }.filter { it != OPTIONS_NODE_PATH }
                }
                execute<CommandSender> { sender, context, _ ->
                    HecioDeath.api().getPenalty()
                        .evaluateNode(context["penaltyId"], context["node"], mapOf("player" to (context.player("player").cast<Player>())))
                    sender.sendLang("Command-Penalty-Node",context["penaltyId"], context["node"])
                }
            }
            execute<CommandSender> { sender, context, _ ->
                HecioDeath.api().getPenalty()
                    .evaluateAllNodes(context["penaltyId"], mapOf("player" to (context.player("player").cast<Player>())))
                sender.sendLang("Command-Penalty-All-Node", context["penaltyId"])
            }
        }
        execute<Player> { sender, context, _ ->
            HecioDeath.api().getPenalty()
                .evaluateAllNodes(context["penaltyId"], mapOf("player" to sender))
            sender.sendLang("Command-Penalty-All-Node", context["penaltyId"])
        }
    }
}