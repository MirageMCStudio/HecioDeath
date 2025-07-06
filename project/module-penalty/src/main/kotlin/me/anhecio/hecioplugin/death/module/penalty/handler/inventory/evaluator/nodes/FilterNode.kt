package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.nodes

import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.AbstractDslNode
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.InventoryContext
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
object FilterNode : AbstractDslNode() {

    override fun handle(context: InventoryContext, args: List<String>){
        val childNodeName = args[0];
        val childNode = getChild(childNodeName);
        // TODO 下一层节点执行
        childNode?.handle(context,args.drop(1))!!
    }

    @Awake(LifeCycle.CONST)
    fun init() {
        InventoryDslEngine.register("filter", this)
    }
}