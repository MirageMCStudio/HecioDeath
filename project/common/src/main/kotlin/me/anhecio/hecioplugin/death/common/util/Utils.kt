package me.anhecio.hecioplugin.death.common.util

import taboolib.common.util.random
import kotlin.math.max

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.util
 *
 * @author Anhecio
 * @since 2025/6/4 14:29
 */
fun String.toPercentage(): Double {
    return this.trim()
        .removeSuffix("%")
        .toDoubleOrNull()
        ?.div(100.0)
        ?: 0.0
}

fun String.toRange(max: Int): Int {
    val (left, right) = this.trim().split("..").map { it.toInt() }
    return random(left, max(left, right))
}
