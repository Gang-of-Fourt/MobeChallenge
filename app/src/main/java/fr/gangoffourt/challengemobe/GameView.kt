package fr.gangoffourt.challengemobe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame
import fr.gangoffourt.challengemobe.minigame.impl.*
import fr.gangoffourt.challengemobe.thread.ThreadGameDraw
import fr.gangoffourt.challengemobe.thread.ThreadWinDelay
import java.lang.Exception
import kotlin.random.Random

class GameView(context: Context): SurfaceHolder.Callback, SurfaceView(context) {

    private var threadGameDraw = ThreadGameDraw(holder, this)
    private var threadWinDelay = ThreadWinDelay(holder,this)
    private var miniGameList = mutableListOf<AbstractMiniGame>()
    private val currentMinigameList = mutableListOf<AbstractMiniGame>()
    private val timeList = mutableListOf<Long>()
    private var currentIndex = 0

    init{
        holder.addCallback(this)
        miniGameList.add(DayNightGame(this, context))
        miniGameList.add(LawyerGame(this, context))
        miniGameList.add(TargetGame(this, context))
        miniGameList.add(ChickGame(this, context))
        miniGameList.shuffle()
        currentMinigameList.addAll(miniGameList)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE) // fond blanc
        miniGameList[currentIndex].draw(canvas)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        threadGameDraw.setRunning(true)
        threadGameDraw.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return miniGameList[currentIndex].onTouchEvent(motionEvent)
    }

    fun solve () {
        timeList.add(System.currentTimeMillis())
        handler.postDelayed(threadWinDelay, 2000)
    }

    fun nextMiniGame() {
        currentIndex ++
        val random = Random.Default
        currentMinigameList.add(miniGameList[random.nextInt(miniGameList.size)])
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry){
            try{
                threadGameDraw.setRunning(false)

                threadGameDraw.join()
                retry = false
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
        }
    }
}