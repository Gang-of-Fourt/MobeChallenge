package fr.gangoffourt.challengemobe

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame
import fr.gangoffourt.challengemobe.minigame.impl.ChickGame
import fr.gangoffourt.challengemobe.minigame.impl.TestGame
import fr.gangoffourt.challengemobe.thread.ThreadGameDraw
import fr.gangoffourt.challengemobe.thread.ThreadUpdateView
import java.lang.Exception

class GameView(context: Context): SurfaceHolder.Callback, SurfaceView(context) {

    private var threadGameDraw = ThreadGameDraw(holder, this)
    private var threadUpdateView = ThreadUpdateView(holder,this)
    private var miniGameList = mutableListOf<AbstractMiniGame>()

    init{
        holder.addCallback(this)
        miniGameList.add(ChickGame(this, context))
    }

    fun update() {
        println("update")
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE) // fond blanc
        miniGameList[miniGameList.size-1].draw(canvas)
    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        threadGameDraw.setRunning(true)
        threadUpdateView.setRunning(true)
        threadGameDraw.start()
        threadUpdateView.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return miniGameList[miniGameList.size-1].onTouchEvent(motionEvent)
    }

    fun solve () {
        Log.d("TEST", "solved !")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (retry){
            try{
                threadGameDraw.setRunning(false)
                threadUpdateView.setRunning(false)

                threadGameDraw.join()
                threadUpdateView.join()
                retry = false
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
        }
    }
}