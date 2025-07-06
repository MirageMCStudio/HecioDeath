package me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets.managers

import me.anhecio.hecioplugin.death.module.penalty.handler.inventory.evaluator.targets.interfaces.IDropExecutor

object DropExecutorManager {

    private val registeredExecutors = HashMap<String, IDropExecutor>();

    fun register(iDropExecutor: IDropExecutor){
        registeredExecutors[iDropExecutor.name] = iDropExecutor;
    }

    fun get(name : String) : IDropExecutor?{
        return registeredExecutors[name.lowercase()];
    }


}