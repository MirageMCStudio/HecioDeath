package me.anhecio.hecioplugin.death.module.penalty.handler.respawn

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.common.util.debug
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import me.anhecio.hecioplugin.death.module.penalty.handler.respawn.data.RespawnType.*
import me.anhecio.hecioplugin.death.module.penalty.handler.respawn.type.RespawnData
import org.bukkit.Location
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration.Companion.toObject
import java.util.UUID

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler
 *
 * @author Anhecio
 * @since 2025/6/3 17:12
 */
object RespawnPenaltyHandler : PenaltyHandler {
    override val name: String = "Respawn"

    val pendingRespawnLocations = mutableMapOf<UUID, Location>()

    override fun penalty(context: Map<String, Any?>, config: ConfigurationSection) {
        val executor = config.getConfigurationSection("executor") ?: return
        val data = executor.toObject<RespawnData>()
        val player = context["player"] as Player

        // 执行自定义重生点
        var location = player.location
        val locationData = data.location

        debug {
            debug(
                """
                    auto: ${data.auto}
                    type: ${data.mode}
                    location:
                        world: ${locationData?.world}
                        x: ${locationData?.x}
                        y: ${locationData?.y}
                        z: ${locationData?.z}
                        yaw: ${locationData?.yaw}
                        pitch: ${locationData?.pitch}
                    """
            )
        }

        // 设置视角
        locationData?.let { data ->
            data.yaw?.let { location.yaw = it.toFloat() }
            data.pitch?.let { location.pitch = it.toFloat() }
        }

        when (data.mode) {
            LOCATION -> {
                locationData?.let { data ->
                    data.x?.let { location.x = it.toDouble() }
                    data.y?.let { location.y = it.toDouble() }
                    data.z?.let { location.z = it.toDouble() }
                }
            }
            DEATH -> {
                // 默认值就是
            }
            SPAWN -> {
                player.bedSpawnLocation?.let { location = it }
            }
        }

        // 缓存给监听器设置重生点
        pendingRespawnLocations.put(player.uniqueId, location)

        // 是否开启自动重生
        if (data.auto) {
            HecioDeath.bukkitScheduler.runTaskLater(HecioDeath.plugin, Runnable {
                player.spigot().respawn()
            }, 1L)
        }
    }

    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }
}