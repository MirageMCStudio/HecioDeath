package me.anhecio.hecioplugin.death.common.impl.command

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.HecioDeathSettings
import me.anhecio.hecioplugin.death.common.impl.command.sub.matcherSubCommand
import me.anhecio.hecioplugin.death.common.impl.command.sub.penaltySubCommand
import me.anhecio.hecioplugin.death.common.util.createTabooLegacyStyleCommandHelper
import me.anhecio.hecioplugin.death.common.util.timing
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang
import taboolib.platform.util.sendLang

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.command
 *
 * @author Anhecio
 * @since 2025/5/31 15:23
 */
@CommandHeader(
    name = "heciodeath",
    aliases = ["hd", "death"],
    permission = "heciodeath.command"
)
object Command {

    @CommandBody
    val main = mainCommand {
        createTabooLegacyStyleCommandHelper()
    }

    @CommandBody
    val matcher = matcherSubCommand

    @CommandBody
    val penalty = penaltySubCommand

    @CommandBody
    val invoke = subCommand {
        dynamic("script", optional = false) {
            execute<CommandSender> { sender, context, _ ->
                val map = mapOf("sender" to sender)
                val result = HecioDeath.api().getJavaScriptHandler().eval(
                    context["script"],
                    map,
                )
                sender.sendLang("Plugin-Result", result ?: "Null")
            }
        }
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            val timing = timing()

            // 重载插件配置管理器
            HecioDeathSettings.setting.reload()
            // 重载匹配管理器
            HecioDeath.api().getMatcher().getConfigManager().reload()
            // 重载惩罚管理器
            HecioDeath.api().getPenalty().getConfigManager().reload()
            // 重载预热脚本配置
            HecioDeath.api().getJavaScriptHandler().preheat()

            sender.sendLang("Plugin-Reloaded", timing(timing))
        }
    }

}