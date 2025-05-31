dependencies {
    compileOnly(project(":project:common"))
    compileOnly(fileTree("libs"))
    compileOnly("cn.lunadeer:DominionAPI:4.3")
}

taboolib { subproject = true }