package me.anhecio.hecioplugin.death.module.penalty.handler.javascript

import me.anhecio.hecioplugin.death.common.HecioDeath
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyHandler
import me.anhecio.hecioplugin.death.module.penalty.api.PenaltyRegistry
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.configuration.ConfigurationSection

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.penalty.handler.javascript
 *
 * @author Anhecio
 * @since 2025/6/4 15:32
 */
object JavaScriptPenaltyHandler : PenaltyHandler {
    override val name: String = "JavaScript"

    override fun penalty(context: Map<String, Any?>, config: ConfigurationSection) {
        val script = config.getString("run") ?: return
        HecioDeath.api().getJavaScriptHandler().run(script, context)
    }

    @Awake(LifeCycle.CONST)
    fun register() {
        PenaltyRegistry.register(this)
    }
}