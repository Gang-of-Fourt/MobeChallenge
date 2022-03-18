package fr.gangoffourt.challengemobe.minigame

import android.graphics.Canvas
import android.hardware.SensorEvent
import fr.gangoffourt.challengemobe.GameView

abstract class AbstractMiniGame(gameView: GameView) {
    abstract fun draw (canvas : Canvas)
    abstract fun sensorEvent(sensorEvent: SensorEvent)
}