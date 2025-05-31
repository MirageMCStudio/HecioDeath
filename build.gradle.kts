import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("io.izzel.taboolib") version "2.0.23" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.22" apply false
}

subprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.izzel.taboolib")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    configure<TabooLibExtension> {
        env {
            install(Basic, Metrics, CommandHelper)
            install(Bukkit, BukkitUtil, BukkitNMSUtil, BukkitHook)
            install(MinecraftEffect, MinecraftChat)
        }
        version { taboolib = "6.2.3-8cc2f66" }
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        compileOnly(kotlin("stdlib"))
        compileOnly("ink.ptms.core:v11604:11604")
        compileOnly("ink.ptms:nms-all:1.0.0")
    }

    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjvm-default=all", "-Xextended-compiler-checks")
        }
    }
}

gradle.buildFinished {
    buildDir.deleteRecursively()
}