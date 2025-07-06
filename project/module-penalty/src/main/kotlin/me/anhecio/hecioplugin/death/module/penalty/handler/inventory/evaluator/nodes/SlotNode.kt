package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes

import me.anhecio.hecioplugin.death.common.compat.data.CompatSlot
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.AbstractDslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryDslEngine
import org.apache.commons.lang.math.NumberRange
import org.bukkit.inventory.ItemStack
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


    override fun handle(context: InventoryContext, args: List<String>){
        val typeName = args[0];
        val slotName = args[1];
        val slots = getSlots(context,typeName,slotName);
        for (key in context.map.keys.toList()){
            if (slots.contains(key)){
                continue
            }
            context.map.remove(key);
        }
    }

    @Awake(LifeCycle.CONST)
    fun init() {
        InventoryDslEngine.register("slot", this)
    }

    // 获取指定种类的指定槽位
    fun getSlots(context : InventoryContext, type : String, line : String) : List<CompatSlot>{
        val slots = if (type.equals("ALL",true)){
            context.map.keys.toList()
        }else{
            context.map.keys.filter { it -> it.slotType.name.equals(type,true) }
        }
        if (line.equals("*")){
            return slots;
        }

        if (line.contains("..")){
            val range = line.split("..");
            val numRange = NumberRange(range[0].toInt(), range[1].toInt());
            return slots.filter { it -> numRange.containsInteger(it.slotId.toInt()) }
        }
        return slots.filter { it -> it.slotId == line };
    }
}