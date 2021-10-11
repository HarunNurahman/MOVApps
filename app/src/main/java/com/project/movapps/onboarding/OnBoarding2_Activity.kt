package com.project.movapps.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.project.movapps.R

class OnBoarding2_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding2)

        val btn_continue = findViewById<Button>(R.id.btn_continue)
        btn_continue.setOnClickListener{
            var intent = Intent(this, OnBoarding3_Activity::class.java)
            startActivity(intent)
        }
    }
}