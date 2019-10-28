package com.irenyescobar.mytasksapp

import com.irenyescobar.mytasksapp.ui.interfaces.WallpaperSettingsInterface
import kotlinx.coroutines.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class WallpaperAnimation(val wallpaper:WallpaperSettingsInterface):CoroutineScope {

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.IO + coroutineJob

    private var papers: ArrayList<Int> = arrayListOf()
    private var index: Int = 0
    private var scheduled: ScheduledFuture<*>? = null
    init {
        launch {
            papers.add(R.drawable.bg1)
            papers.add(R.drawable.bg2)
            papers.add(R.drawable.bg3)
            papers.add(R.drawable.bg4)
            papers.add(R.drawable.bg5)
            papers.add(R.drawable.bg6)
            papers.add(R.drawable.bg7)
        }
    }


    fun start(){
       launch {
           scheduledNextExecution()
       }
    }

    private fun scheduledNextExecution(){
        scheduled = Executors.newSingleThreadScheduledExecutor().schedule({
           execute()
        }, 5, TimeUnit.SECONDS)
    }

    private fun execute(){
        launch {
            if(index >=6){
                index =0
            }
            withContext(Dispatchers.Main){
                wallpaper.setWallpaper(papers[index])
            }
            index += 1
            scheduledNextExecution()
        }
    }
}