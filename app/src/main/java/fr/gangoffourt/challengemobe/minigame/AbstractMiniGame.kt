package fr.gangoffourt.challengemobe.minigame

import android.graphics.Canvas
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.view.MotionEvent
import fr.gangoffourt.challengemobe.GameView

abstract class AbstractMiniGame(gameView: GameView) : SensorEventListener {
    abstract fun draw (canvas : Canvas)
    abstract fun onTouchEvent(motionEvent: MotionEvent): Boolean
}