package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator
 *
 * @author Anhecio
 * @since 2025/6/4 21:11
 */
object InventoryDslEngine {
    private val rootNode = AbstractDslNode()

    fun register(path: String, handler: DslNode) {
        val parts = path.split("::")
        var current = rootNode
        for (i in 0 until parts.lastIndex) {
            val part = parts[i]
            val next = current.getChild(part) ?: AbstractDslNode().also { current.register(part, it) }
            current = next as AbstractDslNode
        }
        current.register(parts.last(), handler)
    }

    fun eval(context: InventoryContext, lines: List<String>) {
        for (line in lines) {
            val args = line.split("::").filter { it.isNotBlank() }
            rootNode.handle(context, args)
        }
    }
}

interface DslNode {
    fun register(childName: String, handler: DslNode)
    fun handle(context: InventoryContext, args: List<String>)
    fun getChild(name: String): DslNode?
}

open class AbstractDslNode : DslNode {
    private val children = mutableMapOf<String, DslNode>()

    override fun register(childName: String, handler: DslNode) {
        children[childName] = handler
    }

    override fun getChild(name: String): DslNode? = children[name]

    override fun handle(context: InventoryContext, args: List<String>) {
        if (args.isEmpty()) return
        val child = getChild(args[0]) ?: return
        child.handle(context, args.drop(1))
    }
}