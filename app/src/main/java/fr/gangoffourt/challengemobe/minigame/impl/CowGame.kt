package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame

class CowGame (gameView: GameView, context: Context) : AbstractMiniGame (gameView, context) {

    private var paint: Paint
    private val imgCow = context.getDrawable(R.drawable.vache)
    private val imgMilk = context.getDrawable(R.drawable.lait)
    init {
        paint = Paint()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.GREEN)
        paint.color = Color.RED
        val canvasBounds = canvas.clipBounds
        var left = canvasBounds.width()
        left /= 2
        var top = canvasBounds.height()
        top /= 2
        if (imgCow != null) {
            left -= imgCow.intrinsicWidth.div(2)
            top -= imgCow.intrinsicHeight.div(2)
            imgCow.setBounds(left, top, left+imgCow.intrinsicWidth, top+imgCow.intrinsicHeight)
            imgCow.draw(canvas)
        }

        if (!holding) {
            left = canvasBounds.width()
            left /= 2
            top = canvasBounds.height()
        } else {
            left = posX
            top = posY
        }
        if (imgMilk != null) {
            left -= imgMilk.intrinsicWidth/4
            top -= imgMilk.intrinsicHeight/4
            imgMilk.setBounds(left, top, left+imgMilk.intrinsicWidth/4, top+imgMilk.intrinsicHeight/4)
            imgMilk.draw(canvas)
        }

    }

    private var holding = false
    private var posX = 0
    private var posY = 0

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_MOVE -> {
                if (holding) {
                    posX = motionEvent.x.toInt()
                    posY = motionEvent.y.toInt()
                } else {
                    if (imgMilk != null) {
                        if (checkClickInBB(motionEvent.x, motionEvent.y, imgMilk.bounds)) {
                            holding = true
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> holding = false
        }
        return true
    }



    private fun checkClickInBB(x:Float, y:Float, boundingBox: Rect) : Boolean {
        var isInBoundingBox = false
        if(x >= boundingBox.left && x <= boundingBox.right && y >= boundingBox.top && y <= boundingBox.bottom){
            isInBoundingBox = true
        }
        return isInBoundingBox
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        TODO("Not yet implemented")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        TODO("Not yet implemented")
    }

}