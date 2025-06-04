package me.anhecio.hecioplugin.death.module.penalty.handler.inventory

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.util.parseToSlotType
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.data.InventoryData
import org.bukkit.entity.Player
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
        val player = context["player"] as Player
        val data = executor.toObject<InventoryData>()

        // 获取玩家所有启用的槽位物品
        val items = HecioDeath.api().getPlayerSlotController().getPlayerAllSlots(player)

        val parsedItems = data.filter?.let {

        }

    }

    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }

}