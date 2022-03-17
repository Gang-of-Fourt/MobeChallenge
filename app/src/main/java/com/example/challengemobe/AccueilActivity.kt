package com.example.challengemobe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.challengemobe.databinding.ActivityAccueilBinding

class AccueilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccueilBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ViewCompat.getWindowInsetsController(window.decorView)?.hide(WindowInsetsCompat.Type.systemBars())
        binding = ActivityAccueilBinding.inflate(layoutInflater)
        binding.buttonLaunch.setOnClickListener(::onClickListener)


        setContentView(binding.root)
    }

    fun onClickListener(v: View?){
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
        finish()
    }
}