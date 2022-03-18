package fr.gangoffourt.challengemobe

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame
import fr.gangoffourt.challengemobe.minigame.Const
import fr.gangoffourt.challengemobe.minigame.impl.*
import fr.gangoffourt.challengemobe.thread.ThreadGameDraw
import fr.gangoffourt.challengemobe.thread.ThreadTimer
import fr.gangoffourt.challengemobe.thread.ThreadWinDelay
import java.lang.Exception
import kotlin.random.Random

class GameView(context: Context): SurfaceHolder.Callback, SurfaceView(context) {

    private var threadGameDraw = ThreadGameDraw(holder, this)
    private var threadWinDelay = ThreadWinDelay(holder,this)
    private var threadTimer = ThreadTimer(holder, this)
    private val currentMinigameList = mutableListOf<AbstractMiniGame>()
    private val timeList = mutableListOf<Long>()
    private var currentIndex = -1
    private var seed = Random.nextLong()
    private val random = Random(seed)
    private val NBGAME = 7
    private var indexArray = 0..NBGAME
    private var delay = 15000L
    private var timeStart = 0L
    private var scoreList = mutableListOf<String>()
    val paint = Paint()

    init{
        holder.addCallback(this)
        paint.textSize = 30F
    }

    fun getDayNightGame() = DayNightGame(this, context)
    fun getLawerGame() = LawyerGame(this, context)
    fun getTargetGame() = TargetGame(this, context)
    fun getChickGame() = ChickGame(this, context)
    fun getBilleGame() = BilleGame(this, context)
    fun getBottleGame() = BottleGame(this, context)
    fun getCowGame() = CowGame(this, context)



    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE) // fond blanc
        currentMinigameList[currentIndex].draw(canvas)

        canvas.drawText("Niveau ${currentIndex+1} / Temps (${(System.currentTimeMillis()-timeStart)/1000}/${calculateTime()/1000} secondes)",50F, canvas.height-50F, paint)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        threadGameDraw.setRunning(true)
        threadGameDraw.start()
        nextMiniGame()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return currentMinigameList[currentIndex].onTouchEvent(motionEvent)
    }

    fun solve () {
        handler.removeCallbacks(threadTimer)
        Log.d("LIST", currentMinigameList.toString())
        currentMinigameList[currentIndex].stop()
        timeList.add(System.currentTimeMillis())
        handler.postDelayed(threadWinDelay, 2000)
    }

    fun fail () {
        val intent = Intent(context, ScoreBoardActivity::class.java)
        Const.addScore("Niveau ${currentIndex+1}")

        var i = 1

        intent.putExtra("scoreList", Const.SCORES)
        context.startActivity(intent)
        (context as AppCompatActivity).finish()
    }

    fun nextMiniGame() {
        currentIndex ++
        timeStart = System.currentTimeMillis()
        currentMinigameList.add(getMiniGame())
        handler.postDelayed(threadTimer, calculateTime())
    }

    private fun calculateTime() = delay - (250 * currentIndex.coerceAtMost(48))

    fun getMiniGame(i : Int = -1) : AbstractMiniGame {
        val rdmint = random.nextInt(NBGAME)
        Log.d("RDM", "$rdmint")
        return when (if (i != -1) i else rdmint) {
            0 -> getChickGame()
            1 -> getDayNightGame()
            2 -> getLawerGame()
            3 -> getTargetGame()
            4 -> getBilleGame()
            5 -> getBottleGame()
            6 -> getCowGame()
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