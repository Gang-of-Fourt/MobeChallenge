package fr.gangoffourt.challengemobe.thread

import android.graphics.Canvas
import android.view.SurfaceHolder
import fr.gangoffourt.challengemobe.GameView
import java.lang.Exception

class ThreadUpdateView(var surfaceHolder: SurfaceHolder, var gameView: GameView) : Thread() {
    private var running = false;

    fun setRunning(isRunning: Boolean){
        running = isRunning;
    }

    fun getDuration() = 250F
    override fun run() {
        var canvas: Canvas? = null

        try {
            canvas = surfaceHolder.lockCanvas()
            synchronized(surfaceHolder){
                gameView.update()
            }
        }
        catch (exception: Exception){
            exception.printStackTrace();
        }
        finally {
            if(canvas != null){
                try {
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
                catch (exception: Exception){
                    exception.printStackTrace()
                }

            }
        }
        gameView.handler.postDelayed(this, this.getDuration().toLong())
    }
}