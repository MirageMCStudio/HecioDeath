package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.modules

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.AbstractDslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryDslEngine
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.modules
 *
 * @author Anhecio
 * @since 2025/6/4 21:22
 */
object SlotNode : AbstractDslNode() {
    @Awake(LifeCycle.CONST)
    fun init() {
        InventoryDslEngine.register("slot", this)
    }
}