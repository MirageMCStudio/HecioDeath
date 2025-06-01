package me.anhecio.hecioplugin.death.common.util

import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.releaseResourceFolder
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Configuration.Companion.getTypeFromExtension
import taboolib.module.configuration.Configuration.Companion.getTypeFromExtensionOrNull
import taboolib.module.configuration.Configuration.Companion.loadFromFile
import taboolib.module.configuration.Type
import java.io.File

/**
 * 获取一个文件夹下所有的文件并转换为Config类型并操作
 *
 * @param file 文件夹
 * @param def 默认类型
 * @param action 配置文件操作
 */
fun readFolderWalkConfig(file: File, action: FolderReader.() -> Unit) {
    FolderReader(file).also(action)
}

/**
 * 灵活释放当前插件内特定目录下的所有资源文件并读取
 * 1. 如果不存在则释放资源文件
 * 2. 如果存在则直接读取文件内容
 *
 * @param path 资源路径
 * @param action 文件操作
 */
fun releaseResourceFolderAndRead(path: String, action: FolderReader.() -> Unit) {
    val file = File(getDataFolder(), path)
    if (!file.exists()) {
        releaseResourceFolder(path)
    }
    FolderReader(file).also(action)
}

data class FolderReader(val file: File) {
    private val readTypes = mutableListOf(Type.YAML)
    private val filter = mutableListOf<File.() -> Boolean>()

    fun setReadType(vararg type: Type) {
        readTypes.clear()
        readTypes.addAll(type)
    }

    fun addFilter(filter: File.() -> Boolean) {
        this.filter.add(filter)
    }

    fun clearFilter() {
        filter.clear()
    }

    fun walk(action: Configuration.() -> Unit) {
        val walk = file.walk()
            .filter { it.isFile }
            .filter { getTypeFromExtensionOrNull(it.extension) in readTypes }
            // from @xiaobai
            .filter { file -> filter.all { it(file) } }

        walk.forEach { inline ->
            loadFromFile(inline, getTypeFromExtension(inline.extension)).also(action)
        }
    }
}

/**
 * 根据文件相对路径获取文件的 Id
 * 例如： ./Vulpecula/script/example/default.yml -> example.default
 */
fun buildFileId(file: File, directory: String): String {
    val directoryFile = File(getDataFolder(), directory)
    val rootPath = directoryFile.toPath().normalize()
    val targetPath = file.toPath().normalize()
    val relativePath = rootPath.relativize(targetPath)
    return relativePath.toString().replace(File.separatorChar, '.').substringBeforeLast('.')
}
/**
 * 获取配置中的所有 ConfigurationSection（仅第一层节点）
 *
 * @return 配置中的所有 ConfigurationSection
 */
fun Configuration.getConfigSections(): List<ConfigurationSection> {
    return getKeys(false).mapNotNull { key -> getConfigurationSection(key) }
}