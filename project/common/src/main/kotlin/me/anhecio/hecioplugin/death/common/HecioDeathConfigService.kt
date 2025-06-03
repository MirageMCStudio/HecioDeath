package me.anhecio.hecioplugin.death.common

import me.anhecio.hecioplugin.death.common.util.buildFileId
import me.anhecio.hecioplugin.death.common.util.releaseResourceFolderAndRead
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type

/**
 * HecioDeath
 * me.anhecio.hecioplugin.death.common
 *
 * @author Anhecio
 * @since 2025/5/31 16:23
 */
class HecioDeathConfigService(
    val directory: String,
    val callback: () -> Unit
) {
    /**
     * 配置缓存
     */
    val cache: MutableMap<String, Configuration> = mutableMapOf()

    /**
     * 加载配置
     */
    fun load() {
        try {
            releaseResourceFolderAndRead(directory) {
                setReadType(Type.YAML)
                walk {
                    cache.put(buildFileId(file!!, directory), this)
                }
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    /**
     * 配置重载
     */
    fun reload() {
        cache.clear()
        load()
        callback()
    }
}