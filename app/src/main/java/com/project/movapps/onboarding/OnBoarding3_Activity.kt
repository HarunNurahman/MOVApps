package com.project.movapps.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.project.movapps.R
import com.project.movapps.sign.signin.SignInActivity

class OnBoarding3_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding3)

        val btn_getstarted = findViewById<Button>(R.id.btn_continue)
        btn_getstarted.setOnClickListener {
            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}