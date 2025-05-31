plugins {
    `maven-publish`
}

taboolib {
    description {
        name(rootProject.name)
        desc("Provide a flexible and powerful death punishment system for the Minecraft Bukkit Server.")
        contributors {
            name("HecioDeath")
        }
    }
}

tasks {
    jar {
        // 构件名
        archiveBaseName.set(rootProject.name)
        // 打包子项目源代码
        rootProject.subprojects.forEach { from(it.sourceSets["main"].output) }
    }
    sourcesJar {
        // 构件名
        archiveBaseName.set(rootProject.name)
        // 打包子项目源代码
        rootProject.subprojects.forEach { from(it.sourceSets["main"].allSource) }
    }
}