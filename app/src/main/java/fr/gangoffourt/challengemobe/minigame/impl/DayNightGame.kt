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


class DayNightGame(private var gameView: GameView, private var context: Context): AbstractMiniGame(gameView, context) {
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private var luminositySensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
    private var night = false
    var imageTemps =  context.getDrawable(R.drawable.timeday)


    init {
        onResume()
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null){
            print("Capteur de luminosité non présent sur le device")
            exitProcess(0)
        }
    }
    override fun draw(canvas: Canvas) {
        imageTemps?.setBounds(0, 0, canvas.width, canvas.height )
        imageTemps?.draw(canvas)
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        return true
    }
    fun changeTime(){
        night = true
        imageTemps =  context.getDrawable(R.drawable.timenight)
        gameView.solve()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (!night){
            when(event.sensor?.type){
                Sensor.TYPE_LIGHT -> {
                    if (event.values[0] <10) {
                        changeTime()
                    }
                }
            }
        }




    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
    fun onResume(){
        sensorManager.registerListener(this, luminositySensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

}