package me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils

import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.CompoundType
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.MatchCondition
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.NumberOperator
import me.anhecio.hecioplugin.death.common.impl.itemmatcher.model.StringOperation
import taboolib.module.chat.uncolored

import taboolib.module.chat.uncolored
import kotlin.text.iterator

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.utils
 *
 * @author Anhecio
 * @since 2025/5/31 17:57
 */
object ParserUtils {

    fun splitConditions(expression: String): List<String> {
        val conditions = mutableListOf<String>()
        val buffer = StringBuilder()
        var braceDepth = 0

        for (c in expression) {
            when (c) {
                '{' -> braceDepth++
                '}' -> braceDepth--
                ';' -> if (braceDepth == 0) {
                    conditions.add(buffer.toString().trim())
                    buffer.clear()
                    continue
                }
            }
            buffer.append(c)
        }
        if (buffer.isNotEmpty()) conditions.add(buffer.toString().trim())
        return conditions
    }

    fun parseKeyValue(condition: String): Pair<String, String>? {
        val eqIndex = condition.indexOf(':')
        if (eqIndex == -1) return null
        return condition.substring(0, eqIndex).trim() to condition.substring(eqIndex + 1).trim()
    }

    fun parseStringCondition(value: String): MatchCondition.StringCondition {
        val pattern = Regex("""(\w+)(?:\[([^]]+)])?\((.*)\)""")
        val match = pattern.matchEntire(value) ?: return MatchCondition.StringCondition(StringOperation.EXACT, listOf(value))

        val operationPart = match.groupValues[1].lowercase()
        // 提取修饰符（可选部分）
        val modifiers = match.groupValues[2].takeIf { it.isNotEmpty() }
        var values = match.groupValues[3].split('|').map { it.trim().removeSurrounding("\"") }

        return when (operationPart) {
            "contains", "con", "c" -> MatchCondition.StringCondition(StringOperation.CONTAINS, values)
            "regex", "reg", "r" -> MatchCondition.StringCondition(StringOperation.REGEX, values)
            "startswith", "start", "s" -> MatchCondition.StringCondition(StringOperation.STARTS_WITH, values)
            "endswith", "end", "e" -> MatchCondition.StringCondition(StringOperation.ENDS_WITH, values)
            else -> MatchCondition.StringCondition(StringOperation.EXACT, values)
        }.also {
            modifiers?.let { modifier ->
                val conf = modifier.lowercase().split(',')
                it.modifiers = conf
                if ("uncolored" in conf || "uc" in conf) {
                    values = values.map { it.uncolored() }
                }
            }
        }
    }

    fun parseListCondition(value: String): MatchCondition {
        return when {
            value.startsWith("any(") || value.startsWith("all(") || value.startsWith("none(") -> {
                val type = when {
                    value.startsWith("any") -> CompoundType.ANY
                    value.startsWith("all") -> CompoundType.ALL
                    value.startsWith("none") -> CompoundType.NONE
                    value.startsWith("not") -> CompoundType.NONE
                    else -> CompoundType.NONE
                }
                // lore:any(contains(b),startsWith(a),regex(^1-9))
                val args = value.substringAfter("(").substringBeforeLast(')')
                    .split(',')
                    .map { it.trim().removeSurrounding("\"") }
                if (args.isEmpty()) return MatchCondition.StringCondition(StringOperation.EXACT, emptyList())
                MatchCondition.CompoundCondition(type, args.map {
                    parseStringCondition(it)
                })
            }

            else -> parseStringCondition(value)
        }
    }

    fun parseNumberListCondition(value: String): MatchCondition {
        return when {
            value.startsWith("any(") || value.startsWith("all(") || value.startsWith("none(") -> {
                val type = when {
                    value.startsWith("any") -> CompoundType.ANY
                    value.startsWith("all") -> CompoundType.ALL
                    value.startsWith("none") -> CompoundType.NONE
                    value.startsWith("not") -> CompoundType.NONE
                    else -> CompoundType.NONE
                }
                // lore:any(contains(b),startsWith(a),regex(^1-9))
                val args = value.substringAfter("(").substringBeforeLast(')')
                    .split(',')
                    .map { it.trim().removeSurrounding("\"") }
                if (args.isEmpty()) return MatchCondition.StringCondition(StringOperation.EXACT, emptyList())
                MatchCondition.CompoundCondition(type, args.map {
                    parseNumberCondition(it)
                })
            }

            else -> parseNumberCondition(value)
        }
    }

    fun parseNumberCondition(value: String): MatchCondition.NumberCondition {
        // 新正则表达式：捕获[前缀][操作符][数值]三部分
        val pattern = Regex("""^(\w+?)?(>=|<=|>|<|=)?(\d+)$""")
        val match = pattern.matchEntire(value) ?: return MatchCondition.NumberCondition(
            operator = NumberOperator.EQUAL,
            value = value.toIntOrNull() ?: 0
        )

        val (rawTag, rawOp, rawValue) = match.destructured
        val tag = rawTag.takeIf { it.isNotEmpty() }
        val operator = when (rawOp) {
            ">=" -> NumberOperator.GREATER_EQUAL
            "<=" -> NumberOperator.LESS_EQUAL
            ">" -> NumberOperator.GREATER
            "<" -> NumberOperator.LESS
            else -> NumberOperator.EQUAL
        }
        val num = rawValue.toIntOrNull() ?: 0

        return MatchCondition.NumberCondition(
            operator = operator,
            value = num
        ).also { it.tag = tag ?: "" }
    }

    // parseNbt("key1=1;key2=2;key3=3")
    fun parseNbt(input: String): Map<String, Any>? {
        return try {
            input.split("&&").associate {
                val (k, v) = it.split('=', limit = 2)
                k.trim() to when {
                    v.toIntOrNull() != null -> v.toInt()
                    v.toDoubleOrNull() != null -> v.toDouble()
                    v.equals("true", true) -> true
                    v.equals("false", true) -> false
                    else -> v.removeSurrounding("\"")
                }
            }
        } catch (e: Exception) {
            null
        }
    }

}