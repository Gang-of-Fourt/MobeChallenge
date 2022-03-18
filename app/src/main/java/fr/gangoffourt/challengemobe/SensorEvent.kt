package fr.gangoffourt.challengemobe

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.system.exitProcess

class SensorEvent(context: Context, private val gameView: GameView) : SensorEventListener {
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private var accelerometerSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private var giroscopeSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private var lightSensor: Sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    init {
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT) == null){
            print("Capteur de luminosité non présent sur le device")
            exitProcess(0)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE) == null){
            print("Gyroscope non présent sur le device")
            exitProcess(0)
        }
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            print("Accelerometre  non présent sur le device")
            exitProcess(0)
        }
    }
    override fun onSensorChanged(event: SensorEvent?) {
        when(event?.sensor?.type){
            Sensor.TYPE_ACCELEROMETER -> {
                print("accelerometre")
            }
            Sensor.TYPE_GYROSCOPE -> {
                print("gyroscope")
            }
            Sensor.TYPE_LIGHT -> {
                print("light")
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    fun onResume(){
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, giroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

}
