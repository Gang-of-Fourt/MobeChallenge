package fr.gangoffourt.challengemobe.minigame.impl

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.view.MotionEvent
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame

class TestGame(gameView: GameView) : AbstractMiniGame(gameView) {
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawRect(0F, 0F, 100F, 100F, paint)
    }

    override fun onTouchEvent(motionEvent: MotionEvent) : Boolean {
        return true
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }
}