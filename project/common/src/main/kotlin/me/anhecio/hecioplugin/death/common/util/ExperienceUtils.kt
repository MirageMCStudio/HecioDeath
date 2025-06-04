package me.anhecio.hecioplugin.death.common.util

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.EntityType
import org.bukkit.entity.ExperienceOrb

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.util
 *
 * @author Anhecio
 * @since 2025/6/4 14:42
 */

/** 累计经验阈值 → 等级 */
fun getIntLevelFromExp(exp: Int): Int {
    var level = 0
    while (getExpFromLevel(level + 1) <= exp) {
        level++
    }
    return level
}

/** 当前等级 → 升下一等级所需经验 */
fun getExpToNext(level: Int): Int = when {
    level <= 15 -> 2 * level + 7
    level <= 30 -> 5 * level - 38
    else         -> 9 * level - 158
}

/** 目标等级 → 累计总经验 */
fun getExpFromLevel(level: Int): Int = when {
    level <= 16 -> level * level + 6 * level
    level <= 31 -> (2.5 * level * level - 40.5 * level + 360).toInt()
    else         -> (4.5 * level * level - 162.5 * level + 2220).toInt()
}

/** 在指定Location掉落指定经验值 */
fun Int.dropExpAt(world: World, location: Location) {
    val orb = world.spawnEntity(location, EntityType.EXPERIENCE_ORB) as ExperienceOrb
    orb.experience = this
}