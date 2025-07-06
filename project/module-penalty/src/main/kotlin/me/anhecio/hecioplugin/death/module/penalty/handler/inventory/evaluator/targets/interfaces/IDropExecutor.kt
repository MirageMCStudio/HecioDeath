package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets.interfaces

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext

abstract class IDropExecutor(name: String) {

    val name : String = name;

    abstract fun execute(originalInventory: InventoryContext,takeItems: InventoryContext, args : Map<String, Any?>);

}