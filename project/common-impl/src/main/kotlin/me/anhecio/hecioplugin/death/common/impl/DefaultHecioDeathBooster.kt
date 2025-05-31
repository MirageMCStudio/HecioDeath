package me.anhecio.hecioplugin.death.common.impl

import me.anhecio.hecioplugin.death.common.HecioDeath
import taboolib.common.util.unsafeLazy

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common.impl
 *
 * @author Anhecio
 * @since 2025/5/31 11:23
 */
object DefaultHecioDeathBooster {

    val api by unsafeLazy { DefaultHecioDeathAPI() }

    /**
     * 启动 HecioDeath 服务
     */
    fun startup() {
        HecioDeath.register(api)
    }

}