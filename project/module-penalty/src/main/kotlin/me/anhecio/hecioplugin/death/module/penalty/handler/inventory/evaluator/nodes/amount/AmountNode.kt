package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes.amount

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.DslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes.FilterNode
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import kotlin.math.roundToInt

object AmountNode : DslNode {
    override fun register(childName: String, handler: DslNode) {
        TODO("Not yet implemented")
    }

    override fun handle(context: InventoryContext, args: List<String>){
        val type = args[0].lowercase();
        val compareTarget = args[1];
        val compareNumber = formatNumber(compareTarget);
        when (type){
            // 百分比
            "percentage" -> {
                for (key in context.map.keys) context.map[key]?.amount?.let { context.map[key]?.amount =
                    (it * compareNumber.toDouble()).roundToInt()
                }
            }
            // 常量
            "constant" -> {
                for (key in context.map.keys) context.map[key]?.amount?.let { context.map[key]?.amount =
                    (it - compareNumber.toDouble()).roundToInt()
                }
            }

        }

    }

    override fun getChild(name: String): DslNode? {
        TODO("Not yet implemented")
    }

    @Awake(LifeCycle.CONST)
    fun init() {
        FilterNode.register("amount", this)
    }
}