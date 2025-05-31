package me.anhecio.hecioplugin.death.common.impl.itemmatcher.model

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl.itemmatcher.model
 *
 * @author Anhecio
 * @since 2025/5/31 17:59
 */
sealed class MatchCondition(
    var modifiers: List<String> = emptyList()
) {
    data class StringCondition(
        val operation: StringOperation,
        var values: List<String>
    ) : MatchCondition()

    data class NumberCondition(
        val operator: NumberOperator,
        val value: Int
    ) : MatchCondition() {
        var tag: String = ""
    }

    data class CompoundCondition(
        val type: CompoundType,
        val conditions: List<MatchCondition>
    ) : MatchCondition()
}

enum class StringOperation { EXACT, CONTAINS, REGEX, STARTS_WITH, ENDS_WITH }
enum class NumberOperator { EQUAL, GREATER, LESS, GREATER_EQUAL, LESS_EQUAL }
enum class CompoundType { ANY, ALL, NONE }