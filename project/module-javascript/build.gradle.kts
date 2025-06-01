dependencies {
    compileOnly(project(":project:common"))
    compileOnly(fileTree("libs/HecioNashorn-1.15.6.jar"))
}

taboolib { subproject = true }