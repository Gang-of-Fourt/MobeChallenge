package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame
import org.xmlpull.v1.XmlSerializer


class BilleGame(var gameView: GameView, context : Context) : AbstractMiniGame(gameView, context) {

    var accelerometre : Sensor
    var sensorManager : SensorManager

    var obstacle1 = Obstacle(2F/8F, 2F/8F, 6F/8F, 3F/8F)
    var obstacle2 = Obstacle(1F/8F, 5F/8F, 3F/8F, 6F/8F)
    var obstacle3 = Obstacle(5F/8F, 5F/8F, 7F/8F, 6F/8F)
    var bille = Bille()
    var endCircle = EndCircle()


    var valAcc = mutableListOf(0F, 0F, 0F)

    init {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometre = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        onResume()
    }

    inner class Bille(){
        var x = 0F
        var y = 0F

        val paint = Paint(Color.BLACK)
        fun draw(canvas: Canvas, valAcc : MutableList<Float>){
            initPos(canvas)
            update(canvas, valAcc)
            canvas.drawCircle(x, y, 50F, paint)
        }

        fun initPos(canvas: Canvas){
            if(x==0F && y ==0F){
                x = canvas.width.toFloat()/2
                y = canvas.height.toFloat()/10
            }
        }

        fun update(canvas: Canvas, valAcc: MutableList<Float> ){
            val xS = x
            val yS = y
            if (!isColision(canvas, this)){
                y += valAcc[1] + valAcc[1] * 2
                x += -valAcc[0] - valAcc[0] * 2
            } else {
                x = xS
                y = yS
            }
        }


    }

    class Obstacle(val left: Float, val top: Float, val right: Float, val bottom : Float){
        fun draw(canvas: Canvas){
            val paint = Paint(Color.BLACK)
            canvas.drawRect(canvas.width.toFloat()*left, canvas.height.toFloat()*top, canvas.width.toFloat()*right, canvas.height.toFloat()*bottom, paint)
        }
    }

    class EndCircle() {
        val radius = 100F

        fun draw(canvas: Canvas) {
            val paint = Paint()
            paint.color = Color.RED
            canvas.drawCircle(canvas.width/2F, canvas.height*7F/8F , radius, paint)
        }
    }


    fun isColision(canvas : Canvas, bille : Bille) : Boolean{
        var yS = bille.y
        var xS = bille.x
        yS += valAcc[1] + valAcc[1] * 2
        if (yS<0 || yS>canvas.height){
            return true
        }

        xS += -valAcc[0] - valAcc[0] * 2
        if (xS<0 || xS>canvas.width){
            return true
        }


        if (checkTouchBoundingBox(xS, yS,
                Rect((obstacle1.left*canvas.width).toInt(),
                    (obstacle1.top*canvas.height).toInt(),
                    (obstacle1.right*canvas.width).toInt(),
                    (obstacle1.bottom*canvas.height).toInt()
                ))) {
            return true
        }
        if (checkTouchBoundingBox(xS, yS,
                Rect((obstacle2.left*canvas.width).toInt(),
                    (obstacle2.top*canvas.height).toInt(),
                    (obstacle2.right*canvas.width).toInt(),
                    (obstacle2.bottom*canvas.height).toInt()
                ))) {
            return true
        }
        if (checkTouchBoundingBox(xS, yS,
                Rect((obstacle3.left*canvas.width).toInt(),
                    (obstacle3.top*canvas.height).toInt(),
                    (obstacle3.right*canvas.width).toInt(),
                    (obstacle3.bottom*canvas.height).toInt()
                ))) {
            return true
        }


        return false
    }

    fun isBilleTouchCircle(canvas: Canvas) : Boolean{

        return checkTouchBoundingBox(bille.x, bille.y,
            Rect(canvas.width*3/9,canvas.height*7/9, canvas.width*6/9, canvas.height*8/9 )
        )
    }

    private fun checkTouchBoundingBox(x: Float, y: Float, boundingBox: Rect): Boolean {
        var isInBoundingBox = false
        if(x >= boundingBox.left && x <= boundingBox.right && y >= boundingBox.top && y <= boundingBox.bottom){
            isInBoundingBox = true
        }
        return isInBoundingBox
    }

    var billeTouched = false

    override fun draw(canvas: Canvas) {
        if (!isBilleTouchCircle(canvas)){
            bille.draw(canvas, valAcc)
            obstacle1.draw(canvas)
            obstacle2.draw(canvas)
            obstacle3.draw(canvas)
            endCircle.draw(canvas)
        } else {
            if (!billeTouched) {
                gameView.solve()
                billeTouched = true
            }
            val paint = Paint(Color.BLACK)
            paint.textSize = 70F
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText("Mais INCROYAAAAAAABLE!", canvas.width/2F, canvas.width/2F, paint)
        }


    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return true
    }

    override fun stop() {
        sensorManager.unregisterListener(this)
    }


    override fun onSensorChanged(event: SensorEvent?) {
        when (event?.sensor?.type) {
            Sensor.TYPE_ACCELEROMETER -> {
                valAcc[0] = event.values[0]
                valAcc[1] = event.values[1]
                valAcc[2] = event.values[2]
            }

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    fun onResume(){
        sensorManager.registerListener(this, accelerometre, SensorManager.SENSOR_DELAY_NORMAL)
    }
}