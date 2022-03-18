package fr.gangoffourt.challengemobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {
    lateinit var gameView: GameView
    lateinit var sensorEvent: SensorEvent


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.systemBars())

        gameView = GameView(this)
        sensorEvent = SensorEvent(this, gameView)

        setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        sensorEvent.onResume()
    }
}