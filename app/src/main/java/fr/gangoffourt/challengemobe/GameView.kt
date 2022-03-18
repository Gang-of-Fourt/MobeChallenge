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
    private var indexArray = 0..3
    private val currentMinigameList = mutableListOf<AbstractMiniGame>()
    private val timeList = mutableListOf<Long>()
    private var currentIndex = 0
    private var seed = Random.nextLong()
    private val random = Random(seed)

    init{
        holder.addCallback(this)
        for (i : Int in indexArray.shuffled()) {
            currentMinigameList.add(getMiniGame(i))
        }

    }

    fun getDayNightGame() = DayNightGame(this, context)
    fun getLawerGame() = LawyerGame(this, context)
    fun getTargetGame() = TargetGame(this, context)
    fun getChickGame() = ChickGame(this, context)

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE) // fond blanc
        currentMinigameList[currentIndex].draw(canvas)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        threadGameDraw.setRunning(true)
        threadGameDraw.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return currentMinigameList[currentIndex].onTouchEvent(motionEvent)
    }

    fun solve () {
        timeList.add(System.currentTimeMillis())
        handler.postDelayed(threadWinDelay, 2000)
    }

    fun fail () {

    }

    fun nextMiniGame() {
        currentIndex ++
        currentMinigameList.add(getMiniGame())
    }

    fun getMiniGame(i : Int = -1) : AbstractMiniGame {
        val rdmint = random.nextInt(4)
        Log.d("RDM", "$rdmint")
        return when (if (i != -1) i else rdmint) {
            0 -> getChickGame()
            1 -> getDayNightGame()
            2 -> getLawerGame()
            3 -> getTargetGame()
            else -> getTargetGame()
        }
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