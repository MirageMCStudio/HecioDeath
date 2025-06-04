package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.modules.slot

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.DslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryDslEngine
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.modules.SlotNode
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.modules.slot
 *
 * @author Anhecio
 * @since 2025/6/4 21:25
 */
object VanillaNode : DslNode {
    override fun register(childName: String, handler: DslNode) {
        TODO("Not yet implemented")
    }

    override fun handle(context: InventoryContext, args: List<String>) {
        TODO("Not yet implemented")
    }

    override fun getChild(name: String): DslNode? {
        TODO("Not yet implemented")
    }

    @Awake(LifeCycle.CONST)
    fun init() {
        InventoryDslEngine.register("slot", this)
    }
}