package com.project.movapps.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.project.movapps.R
import com.project.movapps.sign.signin.SignInActivity
import com.project.movapps.util.Preferences

class OnBoarding1_Activity : AppCompatActivity() {

    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding1)
        preferences = Preferences(this)

        if (preferences.getValues("onboarding").equals("1")) {
            finishAffinity()
            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        val btn_continue = findViewById<Button>(R.id.btn_continue)
        val btn_skip = findViewById<Button>(R.id.btn_skip)
        btn_continue.setOnClickListener {
            var intent = Intent(this, OnBoarding2_Activity::class.java)
            startActivity(intent)
        }
        btn_skip.setOnClickListener {
            preferences.setValues("onboarding", "1")
            finishAffinity()
            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}