package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame


class TestGame(gameView: GameView, context : Context) : AbstractMiniGame(gameView, context) {
    private var mCustomImage: Drawable? = null

    init {
        mCustomImage = context.getDrawable(R.drawable.vache)
    }
    override fun draw(canvas: Canvas) {
        val paint = Paint()
        paint.color = Color.RED
        canvas.drawRect(0F, 0F, 100F, 100F, paint)
        val imageBounds = canvas.getClipBounds()
        mCustomImage?.setBounds(imageBounds)
        mCustomImage?.draw(canvas)
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