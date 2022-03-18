package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.util.Log
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame

class CowGame (private val gameView: GameView, context: Context) : AbstractMiniGame (gameView, context) {

    private var paint: Paint
    private val imgCow = context.getDrawable(R.drawable.vache)
    private val imgMilk = context.getDrawable(R.drawable.lait)
    private val imgWater = context.getDrawable(R.drawable.eau)
    private var isDone = false
    private var isFailed = false
    private var holdingMilk = false
    private var holdingWater = false
    private var xMilk = 0
    private var yMilk = 0
    private var xWater = 0
    private var yWater = 0
    private var textToDisplay = "J'ai soif !"

    init {
        paint = Paint()
    }

    override fun draw(canvas: Canvas) {
        canvas.drawColor(Color.BLACK)
        paint.color = Color.WHITE
        val canvasBounds = canvas.clipBounds
        var left = canvasBounds.width()
        left /= 2
        var top = canvasBounds.height()
        top /= 2
        paint.textSize = 70F;
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText(textToDisplay, left.toFloat(), 90F, paint)

        if (imgCow != null) {
            left -= imgCow.intrinsicWidth.div(2)
            top -= imgCow.intrinsicHeight.div(2)
            imgCow.setBounds(left, top, left+imgCow.intrinsicWidth, top+imgCow.intrinsicHeight)
            imgCow.draw(canvas)
        }

        if (imgMilk != null) {
            if (!holdingMilk) {
                if(!isFailed) {
                    left = canvasBounds.width()
                    left /= 2
                    xMilk = left - imgMilk.intrinsicWidth/4
                    top = canvasBounds.height()
                    yMilk = top - imgMilk.intrinsicHeight/4
                } else {
                    left = xMilk+imgMilk.intrinsicWidth/8
                    top = yMilk+imgMilk.intrinsicHeight/8
                }
            } else {
                left = xMilk+imgMilk.intrinsicWidth/8
                top = yMilk+imgMilk.intrinsicHeight/8
            }
            left -= imgMilk.intrinsicWidth/4
            top -= imgMilk.intrinsicHeight/4
            imgMilk.setBounds(left, top, left+imgMilk.intrinsicWidth/4, top+imgMilk.intrinsicHeight/4)
            imgMilk.draw(canvas)
        }

        if (imgWater != null) {
            if (!holdingWater) {
                if(!isDone) {
                    left = canvasBounds.width()
                    left /= 4
                    xWater = left - imgWater.intrinsicWidth/4
                    top = canvasBounds.height()
                    yWater = top - imgWater.intrinsicHeight/4
                } else {
                    left = xWater+imgWater.intrinsicWidth/8
                    top = yWater+imgWater.intrinsicHeight/8
                }
            } else {
                left = xWater+imgWater.intrinsicWidth/8
                top = yWater+imgWater.intrinsicHeight/8
            }
            left -= imgWater.intrinsicWidth/4
            top -= imgWater.intrinsicHeight/4
            imgWater.setBounds(left, top, left+imgWater.intrinsicWidth/4, top+imgWater.intrinsicHeight/4)
            imgWater.draw(canvas)
        }

    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                if(!isDone) {
                    if (imgMilk != null) {
                        if (checkClickInBB(motionEvent.x, motionEvent.y, imgMilk.bounds)) {
                            holdingMilk = true
                        }
                    }
                    if (imgWater != null) {
                        if (checkClickInBB(motionEvent.x, motionEvent.y, imgWater.bounds)) {
                            holdingWater = true
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (holdingMilk) {
                    xMilk = motionEvent.x.toInt()
                    yMilk = motionEvent.y.toInt()
                } else if (holdingWater) {
                    xWater = motionEvent.x.toInt()
                    yWater = motionEvent.y.toInt()
                }
            }
            MotionEvent.ACTION_UP -> {
                if(holdingWater && checkClickInBB(motionEvent.x, motionEvent.y, Rect(530, 500, 600, 640))) {
                    isDone = true
                    xWater = motionEvent.x.toInt()
                    yWater = motionEvent.y.toInt()
                    textToDisplay = "Ah, ça va mieux, merci."
                    gameView.solve()
                }
                if(holdingMilk && checkClickInBB(motionEvent.x, motionEvent.y, Rect(530, 500, 600, 640))) {
                    isDone = true
                    isFailed = true
                    xMilk = motionEvent.x.toInt()
                    yMilk = motionEvent.y.toInt()
                    textToDisplay = "Oh non :("
                    //TODO ajouter l'appel a failed
                }
                holdingMilk = false
                holdingWater = false
            }
        }
        return true
    }

    override fun stop() {
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

    override fun toString(): String {
        return "CowGame()"
    }

}