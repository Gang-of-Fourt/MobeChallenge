package fr.gangoffourt.challengemobe.minigame.impl

import android.R.attr.left
import android.R.attr.right
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.util.Log
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
        onResume()
        paint.textSize = 70F
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            print("Capteur de luminosité non présent sur le device")
            exitProcess(0)
        }
    }
    override fun draw(canvas: Canvas) {
        socleMarteau?.setBounds(100, canvas.height - 1000, canvas.width - 500, canvas.height - 800)
        socleMarteau?.draw(canvas)
        if (!rotate){
            imageMarteau?.setBounds(300, canvas.height - 1500, canvas.width- 100, canvas.height - 600)
            canvas.drawText("Innoncenter bob", 200f,canvas.height - 300f, paint )

        }
        else {
            canvas.drawText("Innocenté !", 250f,canvas.height - 300f, paint )
            imageMarteau?.setBounds(200, canvas.height - 1600, canvas.width- 200, canvas.height - 600)
        }
        emoji?.setBounds(500, 300, 800, 600)
        emoji?.draw(canvas)
        imageMarteau?.draw(canvas)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return true
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
                if (event.values[0] < -6 && event.values[1] < 6) {
                    changeMarteau()
                }
                if (event.values[0] > 5 && event.values[1] < 6) {
                    changeMarteau()
                }
            }
        }



    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    fun onResume(){
        sensorManager.registerListener(this, accelerometreSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

}