package me.anhecio.hecioplugin.death.common.util

import me.anhecio.hecioplugin.death.common.HecioDeathSettings
import taboolib.common.platform.function.console
import taboolib.module.lang.sendLang

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.util
 *
 * @author Anhecio
 * @since 2025/5/31 15:49
 */

fun debug(text: String) {
    if (HecioDeathSettings.debug) console().sendLang("Plugin-Debug", text)
}

fun debug(block: () -> Unit) {
    if (HecioDeathSettings.debug) block()
}