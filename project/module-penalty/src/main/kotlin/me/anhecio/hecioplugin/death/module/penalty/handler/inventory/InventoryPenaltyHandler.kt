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
import java.util.ArrayList

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.inventory
 *
 * @author Anhecio
 * @since 2025/6/4 17:22
 */
object InventoryPenaltyHandler : PenaltyHandler {
    override val name: String = "Inventory"

    fun splitList(filter: List<String>, splitFont: String): List<List<String>> {
        val result: MutableList<List<String>> = mutableListOf()
        val currentChunk: MutableList<String> = mutableListOf()

        for (item in filter) {
            if (item.equals(splitFont, true)) {
                if (currentChunk.isNotEmpty()) {
                    result.add(currentChunk.toList())
                    currentChunk.clear()
                }
            } else {
                currentChunk.add(item)
            }
        }

        // 添加最后一个块（如果有的话）
        if (currentChunk.isNotEmpty()) {
            result.add(currentChunk.toList())
        }

        return result
    }

    override fun penalty(context: Map<String, Any?>, config: ConfigurationSection) {
        val executor = config.getConfigurationSection("executor") ?: return
        val dropExecutorConfiguration = executor.getConfigurationSection("drop-executor");

        val dropExecutor = DropExecutorManager.get(dropExecutorConfiguration!!.getString("name")!!);
        val player = context["player"] as Player
        val data = InventoryData(executor);


        // 获取玩家所有启用的槽位物品
        val items = HecioDeath.api().getPlayerSlotController().getPlayerAllSlots(player)


        val filter = data.filter!!
        val lists = splitList(filter,"union");

        val finalTakes = InventoryContext(HashMap(),player);
        val originalInventory = InventoryContext(items as MutableMap<CompatSlot, ItemStack>,player);

        for (list in lists){
            val cloneMap : MutableMap<CompatSlot, ItemStack> = HashMap();
            for (slot in items.keys){
                if (items[slot] != null){
                    cloneMap[slot] = items[slot]!!.clone()
                }
            }
            val takeItems = InventoryContext(cloneMap,player);

            InventoryDslEngine.eval(takeItems,list);
            finalTakes.map.putAll(takeItems.map)
        }

        val argsConfig = dropExecutorConfiguration.getConfigurationSection("args");
        var args: Map<String, Any?> = HashMap();
        if (argsConfig != null){
            args = argsConfig.toMap();
        }
        dropExecutor?.execute(originalInventory,finalTakes, args);


    }

    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }

}