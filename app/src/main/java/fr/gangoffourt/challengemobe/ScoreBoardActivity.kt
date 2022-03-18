package fr.gangoffourt.challengemobe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.challengemobe.R
import com.example.challengemobe.databinding.ActivityAccueilBinding
import com.example.challengemobe.databinding.ActivityScoreBoardBinding

class ScoreBoardActivity : AppCompatActivity() {

    var scoreList = mutableListOf<String>()
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_board)
        button = findViewById(R.id.buttonBack)
        button.text = "Menu"
        button.setOnClickListener(::onClick)
        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.systemBars())

        val extras = intent.extras
        scoreList = extras?.get("scoreList") as MutableList<String>

        title = "Tableau des scores"

        val listView = findViewById<ListView>(R.id.simpleListView)
        var arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, scoreList
        )
        listView.adapter = arrayAdapter
    }

    fun onClick(v: View) {
        val intent = Intent(this, AccueilActivity::class.java)
        startActivity(intent)
        finish()
    }
}