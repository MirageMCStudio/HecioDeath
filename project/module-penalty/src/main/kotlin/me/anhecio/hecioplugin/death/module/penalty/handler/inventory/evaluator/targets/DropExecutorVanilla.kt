package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryDslEngine
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets.interfaces.IDropExecutor
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets.managers.DropExecutorManager
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

object DropExecutorVanilla : IDropExecutor("vanilla") {
    override fun execute(
        originalInventory : InventoryContext,
        takeItems: InventoryContext,
        args: Map<String, Any?>
    ) {
        val loc = takeItems.player.location;
        val world = loc.world;
        for (slot in takeItems.map.keys){
            val item = originalInventory.map[slot];
            // 扣除实际背包的物品
            item?.amount?.let { item.amount = it - takeItems.map[slot]!!.amount };
            // 掉落
            world.dropItem(loc,takeItems.map[slot]!!);
        }
    }


    @Awake(LifeCycle.CONST)
    fun init() {
        DropExecutorManager.register(this)
    }
}