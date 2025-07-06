package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes.amount

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.DslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes.FilterNode
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object MaterialNode : DslNode {
    override fun register(childName: String, handler: DslNode) {
        TODO("Not yet implemented")
    }

    override fun handle(context: InventoryContext, args: List<String>){

        // TODO 物品匹配器

    }

    override fun getChild(name: String): DslNode? {
        TODO("Not yet implemented")
    }

    @Awake(LifeCycle.CONST)
    fun init() {
        FilterNode.register("material", this)
    }
}