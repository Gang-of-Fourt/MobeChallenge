package fr.gangoffourt.challengemobe.minigame.data

import android.graphics.Paint

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