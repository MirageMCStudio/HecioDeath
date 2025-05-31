package me.anhecio.hecioplugin.death.common

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 11:08
 */
object HecioDeath {
    private var api: HecioDeathAPI? = null

    /**
     * 获取开发者接口
     */
    fun api(): HecioDeathAPI {
        return api ?: error("HecioDeathAPI has not finished loading, or failed to load!")
    }

    /**
     * 注册开发者接口
     */
    fun register(api: HecioDeathAPI) {
        HecioDeath.api = api
    }
}