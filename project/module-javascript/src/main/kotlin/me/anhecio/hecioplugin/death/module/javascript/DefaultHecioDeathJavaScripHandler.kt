package me.anhecio.hecioplugin.death.module.javascript

import me.anhecio.hecioplugin.death.common.HecioDeathJavaScriptHandler
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.PlatformFactory

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.module.javascript
 *
 * @author Anhecio
 * @since 2025/5/31 20:01
 */
class DefaultHecioDeathJavaScripHandler : HecioDeathJavaScriptHandler {



    companion object {
        @Awake(LifeCycle.CONST)
        fun init() {
            PlatformFactory.registerAPI<HecioDeathJavaScriptHandler>(DefaultHecioDeathJavaScripHandler())
        }
    }
}