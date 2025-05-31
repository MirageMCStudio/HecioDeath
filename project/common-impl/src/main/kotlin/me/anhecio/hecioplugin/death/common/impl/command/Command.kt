package me.anhecio.hecioplugin.death.common.impl.command

import me.anhecio.hecioplugin.death.common.util.createTabooLegacyStyleCommandHelper
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.common.platform.command.subCommand

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.command
 *
 * @author Lumira311
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
    val reload = subCommand {

    }

}