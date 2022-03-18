package fr.gangoffourt.challengemobe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.challengemobe.R
import com.example.challengemobe.databinding.ActivityAccueilBinding
import com.example.challengemobe.databinding.ActivityScoreBoardBinding

class ScoreBoardActivity : AppCompatActivity() {

    var scoreList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.systemBars())
        setContentView(R.layout.activity_score_board)
        val extras = intent.extras
        scoreList = extras?.get("scoreList") as MutableList<String>

        title = "Tableau des scores"

        // access the listView from xml file
        val listView = findViewById<ListView>(R.id.simpleListView)
        var arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, scoreList
        )
        listView.adapter = arrayAdapter
    }
}