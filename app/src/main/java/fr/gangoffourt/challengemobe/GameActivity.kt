package fr.gangoffourt.challengemobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GameActivity : AppCompatActivity() {

    lateinit var gameView: GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.systemBars())

        gameView = GameView(this)

        setContentView(gameView)
    }
}