package com.project.movapps.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.project.movapps.R
import com.project.movapps.home.HomeActivity

class TopUpSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup_success)

        val home: Button = findViewById(R.id.btn_home)
        val wallet: Button = findViewById(R.id.btn_back_wallet)

        home.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }

        wallet.setOnClickListener {
            startActivity(Intent(this, MyWalletActivity::class.java))
        }
    }
}