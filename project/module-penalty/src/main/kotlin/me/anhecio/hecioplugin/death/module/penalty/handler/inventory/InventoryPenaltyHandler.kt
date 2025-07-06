package me.anhecio.hecioplugin.death.module.penalty.handler.inventory

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import me.anhecio.hecioplugin.death.common.util.parseToSlotType
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.data.InventoryData
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryDslEngine
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets.managers.DropExecutorManager
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration.Companion.toObject

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory
 *
 * @author Anhecio
 * @since 2025/6/4 17:22
 */
object InventoryPenaltyHandler : PenaltyHandler {
    override val name: String = "Inventory"

    override fun penalty(context: Map<String, Any?>, config: ConfigurationSection) {
        val executor = config.getConfigurationSection("executor") ?: return
        val dropExecutorConfiguration = executor.getConfigurationSection("drop-executor");

        val dropExecutor = DropExecutorManager.get(dropExecutorConfiguration!!.getString("name")!!);
        val player = context["player"] as Player
        val data = InventoryData(executor);

        // 获取玩家所有启用的槽位物品
        val items = HecioDeath.api().getPlayerSlotController().getPlayerAllSlots(player)
        val cloneMap : MutableMap<CompatSlot, ItemStack> = HashMap();
        for (slot in items.keys){
            if (items[slot] != null){
                cloneMap[slot] = items[slot]!!.clone()
            }
        }
        val takeItems = InventoryContext(cloneMap,player);
        InventoryDslEngine.eval(takeItems,data.filter!!);

        val originalInventory = InventoryContext(items as MutableMap<CompatSlot, ItemStack>,player);
        val argsConfig = dropExecutorConfiguration.getConfigurationSection("args");
        var args: Map<String, Any?> = HashMap();
        if (argsConfig != null){
            args = argsConfig.toMap();
        }
        dropExecutor?.execute(originalInventory,takeItems, args);


    }

    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }

}