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


class BottleGame(private var gameView: GameView, private var context: Context): AbstractMiniGame(gameView, context) {
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var accelerometreSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var win = false
    var imageBouteille =  context.getDrawable(R.drawable.bouteille_2)

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
        val canvasBounds = canvas.clipBounds
        imageBouteille?.setBounds(100, canvas.height - 1000, canvas.width - 500, canvas.height - 800)
        var left = canvasBounds.width()
        left /= 2
        var top = canvasBounds.height()
        top /= 2
        if(!win)
            canvas.drawText("Videz la bouteille", left/2f, 100f, paint)
        else
            canvas.drawText("Merci!", left - 10f, 100f, paint)

        if (imageBouteille != null) {
            left -= imageBouteille!!.intrinsicWidth.div(2)
            top -= imageBouteille!!.intrinsicHeight.div(2)
            imageBouteille!!.setBounds(left, top, left+imageBouteille!!.intrinsicWidth, top+imageBouteille!!.intrinsicHeight)
            imageBouteille!!.draw(canvas)
        }
        imageBouteille!!.draw(canvas)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return true
    }

    private var test = 0
    private var  bigInHold = System.currentTimeMillis()

    override fun onSensorChanged(event: SensorEvent) {
        when(event.sensor?.type){
            Sensor.TYPE_ACCELEROMETER -> {
                if (event.values[1] < -8) {
                    if (test == 0) {
                        bigInHold = System.currentTimeMillis()
                        test= 1
                    }
                    if (System.currentTimeMillis() - bigInHold > 500)
                        imageBouteille =  context.getDrawable(R.drawable.bouteille_1)
                    if (System.currentTimeMillis() - bigInHold > 1000)
                        imageBouteille =  context.getDrawable(R.drawable.bouteille_0)
                    if (System.currentTimeMillis() - bigInHold > 1500){
                        imageBouteille =  context.getDrawable(R.drawable.bouteille)
                        if (!win)
                            gameView.solve()
                        win = true
                    }


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