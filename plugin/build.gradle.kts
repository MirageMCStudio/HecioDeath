plugins {
    `maven-publish`
}

taboolib {
    description {
        name(rootProject.name)
        desc("Provide a flexible and powerful death punishment system for the Minecraft Bukkit Server.")
        contributors {
            name("Anhecio")
            name("FROSTM0URNE")
        }
        dependencies {
            name("DragonCore").optional(true)
            name("GermPlugin").optional(true)
            name("ArcartX").optional(true)
            name("Residence").optional(true)
            name("WorldGuard").optional(true)
            name("Dominion").optional(true)
        }
    }
}

dependencies {
    taboo(files("libs/HecioNashorn-1.15.6.jar"))
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