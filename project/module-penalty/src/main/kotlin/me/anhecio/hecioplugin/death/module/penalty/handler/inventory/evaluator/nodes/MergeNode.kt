package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.AbstractDslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryDslEngine
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object MergeNode : AbstractDslNode() {

    @Awake(LifeCycle.CONST)
    fun init() {
        InventoryDslEngine.register("merge", this)
    }
}