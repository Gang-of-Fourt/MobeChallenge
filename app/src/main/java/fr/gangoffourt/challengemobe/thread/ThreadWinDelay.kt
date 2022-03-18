package fr.gangoffourt.challengemobe.thread

import android.view.SurfaceHolder
import fr.gangoffourt.challengemobe.GameView

class ThreadWinDelay(var surfaceHolder: SurfaceHolder, var gameView: GameView) : Thread() {
    private var running = false;

    fun setRunning(isRunning: Boolean){
        running = isRunning
    }

    override fun run() {
        gameView.nextMiniGame()
    }
}