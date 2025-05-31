package me.anhecio.hecioplugin.death.module.matcher.handler

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.compat.type.PlayerSlotType
import me.anhecio.hecioplugin.death.common.util.parseToSlotType
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherHandler
import me.anhecio.hecioplugin.death.module.matcher.api.MatcherRegistry
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import sun.audio.AudioPlayer.player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.matcher.handler
 *
 * @author Anhecio
 * @since 2025/5/31 18:08
 */
object ItemMatcherHandler : MatcherHandler {
    override val name: String = "Item"

    override fun match(event: PlayerDeathEvent, config: ConfigurationSection): Boolean {
        val requires = config.getConfigurationSection("requires") ?: return true
        val player = event.entity.player!!

        // 获取玩家槽位控制器
        val controller = HecioDeath.api().getPlayerSlotController()
        // 获取玩家所有槽位物品
        val inventory = controller.getPlayerAllSlots(player)
        // 将槽位物品转换为ItemStack列表 用于判断背包物品匹配
        val items = inventory.values.toList()
        // 获取物品匹配器
        val matcher = HecioDeath.api().getItemMatcher()

        requires.getStringList("has").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.any { !matcher.contains(items, it) }) return false
        }

        requires.getStringList("has-only").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.none { matcher.contains(items, it) }) return false
        }

        requires.getStringList("lack").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.any { matcher.contains(items, it) }) return false
        }

        requires.getStringList("lack-only").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.none { !matcher.contains(items, it) }) return false
        }

        // type::slot::match
        // Vanilla::0::name:all(startswith(&c机械),c(靴))
        requires.getStringList("slot-has").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.any {  matchConfig ->
                    val (type, slot, match) = matchConfig.split("::")
                    val item = controller.getPlayerSlot(player, controller.toCompatSlot(slot, type.parseToSlotType()))
                    item?.let { !matcher.match(it, match) } == true
                }) return false
        }

        requires.getStringList("slot-has-only").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.none { matchConfig ->
                    val (type, slot, match) = matchConfig.split("::")
                    val  item = controller.getPlayerSlot(player, controller.toCompatSlot(slot, type.parseToSlotType()))
                    item?.let { matcher.match(it, match) } == true
                }) return false
        }

        requires.getStringList("slot-lack").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.any {  matchConfig ->
                    val (type, slot, match) = matchConfig.split("::")
                    val item = controller.getPlayerSlot(player, controller.toCompatSlot(slot, type.parseToSlotType()))
                    item?.let { matcher.match(it, match) } == true
                }) return false
        }

        requires.getStringList("slot-lack-only").takeIf { it.isNotEmpty() }?.let { matchList ->
            if (matchList.none { matchConfig ->
                    val (type, slot, match) = matchConfig.split("::")
                    val  item = controller.getPlayerSlot(player, controller.toCompatSlot(slot, type.parseToSlotType()))
                    item?.let { !matcher.match(it, match) } == true
                }) return false
        }

        return true
    }

    @Awake(LifeCycle.CONST)
    fun register() {
        MatcherRegistry.register(this)
    }
}