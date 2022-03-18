package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame
import kotlin.system.exitProcess


class LawyerGame(private var gameView: GameView,private var context: Context): AbstractMiniGame(gameView, context) {

    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometreSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var rotate = false
    var imageMarteau =  context.getDrawable(R.drawable.marteau)
    val socleMarteau = context.getDrawable(R.drawable.support_juge)
    var emoji = context.getDrawable(R.drawable.sad)
    val paint = Paint()

    init {
        init()
        paint.textSize = 70F
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            print("Capteur de luminosité non présent sur le device")
            exitProcess(0)
        }
    }

    override fun draw(canvas: Canvas) {
        val imageBounds = canvas.getClipBounds()

        var leftSocle: Float = imageBounds.width().toFloat()
        leftSocle /= 4
        var topSocle: Float = imageBounds.height().toFloat()
        topSocle /= 2

        var leftMarteau: Float = imageBounds.width().toFloat()
        leftMarteau /= 2
        var topMarteau: Float = imageBounds.height().toFloat()
        topMarteau /= 2.5F

        var leftEmoji: Float = imageBounds.width().toFloat()
        leftEmoji /= 2
        var topEmoji: Float = imageBounds.height().toFloat()
        topEmoji /= 2F

        if(socleMarteau != null) {
            topSocle -= (socleMarteau.intrinsicHeight.div(2))
            leftSocle -= (socleMarteau.intrinsicWidth.div(2))

            socleMarteau?.setBounds(
                leftSocle.toInt(),
                topSocle.toInt(),
                leftSocle.toInt()+socleMarteau.intrinsicWidth,
                topSocle.toInt()+socleMarteau.intrinsicHeight)
            socleMarteau?.draw(canvas)
        }

        if (!rotate){
            if(imageMarteau != null) {

                topMarteau -= (imageMarteau!!.intrinsicHeight.div(2))
                leftMarteau -= (imageMarteau!!.intrinsicWidth.div(2))

                imageMarteau?.setBounds(
                    leftMarteau.toInt(),
                    topMarteau.toInt(),
                    leftMarteau.toInt()+ imageMarteau!!.intrinsicWidth,
                    topMarteau.toInt()+ imageMarteau!!.intrinsicHeight
                )
            }

            canvas.drawText("Innoncenter bob", 200f,canvas.height - 300f, paint )

        }
        else {
            canvas.drawText("Innocenté !", 250f,canvas.height - 300f, paint )
            if(imageMarteau != null) {

                topMarteau -= (imageMarteau!!.intrinsicHeight.div(2))
                leftMarteau -= (imageMarteau!!.intrinsicWidth.div(1.5F))

                imageMarteau?.setBounds(
                    leftMarteau.toInt(),
                    topMarteau.toInt(),
                    leftMarteau.toInt()+ imageMarteau!!.intrinsicWidth,
                    topMarteau.toInt()+ imageMarteau!!.intrinsicHeight
                )
            }
        }

        if(emoji != null) {
            topEmoji -= (emoji!!.intrinsicHeight.div(1.3F))
            leftEmoji -= (emoji!!.intrinsicWidth.div(2))

            emoji?.setBounds(
                leftEmoji.toInt(),
                topEmoji.toInt(),
                leftEmoji.toInt()+ emoji!!.intrinsicWidth/4,
                topEmoji.toInt()+ emoji!!.intrinsicHeight/4
            )
            emoji?.draw(canvas)
        }

        imageMarteau?.draw(canvas)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return true
    }

    override fun stop() {
        sensorManager.unregisterListener(this)
    }

    fun changeMarteau(){

        if (!rotate) {
            gameView.solve()
        }
        rotate = true
        imageMarteau =  context.getDrawable(R.drawable.marteau_rotate)
        emoji = context.getDrawable(R.drawable.happy)

    }

    override fun onSensorChanged(event: SensorEvent) {
        when(event.sensor?.type){
            Sensor.TYPE_ACCELEROMETER -> {
                if (event.values[0] < -6 && event.values[1] < 4) {
                    changeMarteau()
                }
                if (event.values[0] > 6 && event.values[1] < 4) {
                    changeMarteau()
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    fun init(){
        sensorManager.registerListener(this, accelerometreSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun toString(): String {
        return "LawyerGame()"
    }

}