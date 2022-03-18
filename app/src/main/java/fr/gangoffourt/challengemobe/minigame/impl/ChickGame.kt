package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.util.Log
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame
import kotlin.math.abs

class ChickGame(private val gameView: GameView, private val context : Context) : AbstractMiniGame(gameView, context) {

    private var chickImage: Drawable? = null
    private val baseDuration: Long = 2000
    private var scratchDuration: Long = 2000
    private var timeWhenPressed: Long? = null
    private var textToDisplay = "J'ai le bidon qui gratte !"
    private var isDone = false
    private var previousX = 0F
    private var previousY = 0F
    private val delta = 10
    private var counter = 0

    init {
        chickImage = context.getDrawable(R.drawable.chick)
    }

    override fun draw(canvas: Canvas) {
        val paint = Paint()
        val imageBounds = canvas.getClipBounds()
        var left: Float = imageBounds.width().toFloat()
        left /= 2
        var top: Float = imageBounds.height().toFloat()
        top /= 2
        paint.textSize = 70F;
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(textToDisplay, left, 90F, paint)
        if(chickImage != null) {
            top -= (chickImage!!.intrinsicHeight.div(2))
            left -= (chickImage!!.intrinsicWidth.div(2))
            chickImage?.setBounds(
                left.toInt(),
                top.toInt(),
                (left+chickImage!!.intrinsicWidth).toInt(),
                (top+chickImage!!.intrinsicHeight).toInt()
            )
            chickImage?.draw(canvas)
        }
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        if(!isDone) {
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    if(chickImage != null) {
                        if(checkTouchBoundingBox(motionEvent.x, motionEvent.y, chickImage!!.bounds)) {
                            timeWhenPressed = System.currentTimeMillis()
                            textToDisplay = "Hum oui comme ça !"
                        }
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                    if(abs(motionEvent.x - previousX) > delta || abs(motionEvent.y - previousY) > delta) {
                        previousX = motionEvent.x
                        previousY = motionEvent.y
                        if(checkTouchBoundingBox(motionEvent.x, motionEvent.y, chickImage!!.bounds)) {
                            counter += 1
                        } else {
                            counter = 0
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if(chickImage != null) {
                        if(checkTouchBoundingBox(motionEvent.x, motionEvent.y, chickImage!!.bounds)) {
                            scratchDuration = timeWhenPressed?.let { System.currentTimeMillis().minus(it) }!!
                            if(scratchDuration >= baseDuration && counter >= 10) {
                                gameView.solve()
                                textToDisplay = "Merci !"
                                isDone = true
                            } else {
                                scratchDuration = 0
                                textToDisplay = "J'ai le bidon qui gratte !"
                                counter = 0
                            }

                        } else {
                            scratchDuration = 0
                            textToDisplay = "J'ai le bidon qui gratte !"
                            counter = 0
                        }
                    }
                }
            }
        }
        return true
    }

    override fun stop() {
    }

    private fun checkTouchBoundingBox(x: Float, y: Float, boundingBox: Rect): Boolean {
        var isInBoundingBox = false
        if(x >= boundingBox.left && x <= boundingBox.right && y >= boundingBox.top && y <= boundingBox.bottom){
            isInBoundingBox = true
        }
        return isInBoundingBox
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        throw NotImplementedError()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        throw NotImplementedError()
    }

    override fun toString(): String {
        return "ChickGame()"
    }

}