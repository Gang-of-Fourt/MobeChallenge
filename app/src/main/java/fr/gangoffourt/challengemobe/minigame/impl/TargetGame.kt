package fr.gangoffourt.challengemobe.minigame.impl

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.util.Log
import android.view.MotionEvent
import com.example.challengemobe.R
import fr.gangoffourt.challengemobe.GameView
import fr.gangoffourt.challengemobe.minigame.AbstractMiniGame


class Circle(var nom : String, var x: Float, var y: Float, var color: Int){
    val paint = Paint()
    init {
        setPaint()
    }
    fun setPaint(){
        paint.color = color
        paint.textSize = 70F
    }
}

class TargetGame(private var gameView: GameView, private var context: Context): AbstractMiniGame(gameView, context) {
    var end = false
    var heightScreen = 0
    var widthScreen = 0
    var cercles : MutableList<Circle> = mutableListOf()
    var SIZE_TARGET = 150f
    private var night = false
    var imageTemps =  context.getDrawable(R.drawable.timeday)
    var bigInHold = 0L
    var delta = 150
    var cle =  context.getDrawable(R.drawable.cle)
    var cleFind = false

    init{



    }
    fun initialisationCercle(){
        cercles.add( Circle("c0", 300f, heightScreen/4f, Color.RED))
        cercles.add(Circle("c1", widthScreen-300f, heightScreen / 8f, Color.BLUE))
        cercles.add( Circle("c2", widthScreen-600f, heightScreen / 2f, Color.GREEN))
        cercles.add( Circle("c3",widthScreen / 2f, heightScreen - 300f, Color.YELLOW))
    }
    override fun draw(canvas: Canvas) {
        if(heightScreen == 0 && widthScreen == 0){
            heightScreen = canvas.height
            widthScreen = canvas.width
            initialisationCercle()
        }

        cle?.setBounds(widthScreen-400, heightScreen / 8 - 50, widthScreen-400 + 150, heightScreen / 8+100 )
        cle?.draw(canvas)

        if (end) canvas.drawColor(Color.RED)
        for (elem in cercles){
            Log.d("TEST", cercles.toString())
            canvas.drawCircle(elem.x, elem.y, SIZE_TARGET, elem.paint)
        }



    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {

        when(motionEvent.action){
            MotionEvent.ACTION_DOWN -> {
                bigInHold = System.currentTimeMillis()
                checkClick(motionEvent.x, motionEvent.y)
            }
            MotionEvent.ACTION_MOVE -> {
                if (System.currentTimeMillis() - bigInHold > 500)
                    moovePiece(motionEvent.x, motionEvent.y)
            }
            MotionEvent.ACTION_UP -> {
                delta = 150
            }
        }
        return true
    }
    fun moovePiece(x:Float, y: Float){
        for (target in cercles){
                if(x >= target.x-SIZE_TARGET && x <= target.x + SIZE_TARGET && y >= target.y-SIZE_TARGET && y <= target.y+ SIZE_TARGET){
                    target.x = x
                    target.y = y
                    if (target.nom == "c1"){
                        cleFind = true
                    }
                }
            }
        }

    fun checkClick(x:Float, y: Float){
        if (cleFind) {
            if (x >= widthScreen - 400 && x <= widthScreen - 400 + 150 && y >= heightScreen / 8 - 50 && y <= heightScreen / 8 + 100) {
                end = true
                gameView.solve()
            }
        }

        for (target in cercles){
            if(x >= target.x-SIZE_TARGET && x <= target.x + SIZE_TARGET && y >= target.y-SIZE_TARGET && y <= target.y+ SIZE_TARGET){
                target.color = Color.BLACK
                target.setPaint()

            }
        }
    }
    override fun onSensorChanged(p0: SensorEvent?) {
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }


}