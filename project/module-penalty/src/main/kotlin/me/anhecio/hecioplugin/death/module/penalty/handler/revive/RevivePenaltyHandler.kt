package me.anhecio.hecioplugin.death.module.penalty.handler.revive

import kotlinx.coroutines.delay
import me.anhecio.hecioplugin.death.common.util.timing
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import me.anhecio.hecioplugin.death.module.penalty.handler.revive.data.ReviveData
import org.bukkit.entity.Player
import org.bukkit.event.entity.PlayerDeathEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration.Companion.toObject
import taboolib.platform.util.kill
import java.util.UUID

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.revive
 *
 * @author Anhecio
 * @since 2025/6/5 13:56
 */
object RevivePenaltyHandler : PenaltyHandler {
    override val name: String = "Revive"

    /**
     * 记录处在Revive状态的玩家以及开始时间 (世纪毫秒)
     */
    val revivalPlayers = mutableMapOf<UUID, Long>()

    override fun penalty(context: Map<String, Any?>, config: ConfigurationSection) {
        val player = context["player"] as Player
        // 判断是否是本惩罚器触发的死亡
        if (player.scoreboardTags.contains("HecioDeath_Revive_Handler")) return

        val executor = config.getConfigurationSection("executor") ?: return
        val data = executor.toObject<ReviveData>()
        val event = context["event"]?.let { it as PlayerDeathEvent }

        // 如果是死亡触发就先把死亡事件取消
        try {
            event?.let { it.isCancelled = true }
        } catch (ex: Throwable) {
            error("当前服务端核心暂不支持 $name 惩罚器: ${ex.message}")
        }

        // 缓存时间 后续用于本地化
        revivalPlayers.put(player.uniqueId, timing())

        val refresh = submit(period = (data.refresh.toInt() * 20).toLong()) {
            val cd = (timing(timing()) / 1000).toInt()
            player.sendMessage("你还有 $cd 秒就会彻底死亡.")
        }

        submit(delay = (data.cooldown.toInt() * 20).toLong()) {
            refresh.cancel()
            revivalPlayers.remove(player.uniqueId)
            player.addScoreboardTag("HecioDeath_Revive_Handler")
            player.kill()
        }
    }

    private fun runRevive() {

    }


    private fun stopRevive() {

    }



    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }
}