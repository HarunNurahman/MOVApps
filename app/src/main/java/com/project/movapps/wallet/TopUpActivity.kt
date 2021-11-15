package com.project.movapps.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.project.movapps.R
import com.project.movapps.home.HomeActivity

class TopUpActivity : AppCompatActivity() {

    private var status10K : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)
        var topup: Button = findViewById(R.id.btn_topup)
        var ten: TextView = findViewById(R.id.tp_10k)

        topup.setOnClickListener {
            startActivity(Intent(this, TopUpSuccessActivity::class.java))
        }

        ten.setOnClickListener {
            if (status10K){
                deselectAmount(ten)
            } else {
                selectAmount(ten)
            }
        }

    }

    private fun selectAmount(textView: TextView){
        textView.setTextColor(resources.getColor(R.color.primarybtn))
        textView.setBackgroundResource(R.drawable.shape_top_up_red)
        status10K = true

        var topup: Button = findViewById(R.id.btn_topup)
        topup.visibility = View.VISIBLE
    }

    private fun deselectAmount(textView: TextView){
        textView.setTextColor(resources.getColor(R.color.white))
        textView.setBackgroundResource(R.drawable.shape_top_up)
        status10K = false

        var topup: Button = findViewById(R.id.btn_topup)
        topup.visibility = View.INVISIBLE
    }

}