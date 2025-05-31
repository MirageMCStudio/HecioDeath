package me.anhecio.hecioplugin.death.common.util

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.util
 *
 * @author Lumira311
 * @since 2025/5/31 15:20
 */
fun timing(): Long {
    return System.currentTimeMillis()
}

fun timing(start: Long): Long {
    return  System.currentTimeMillis() - start
}