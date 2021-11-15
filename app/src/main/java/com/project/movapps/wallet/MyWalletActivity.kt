package com.project.movapps.wallet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.movapps.R
import com.project.movapps.wallet.adapter.WalletAdapter
import com.project.movapps.wallet.model.Wallet

class MyWalletActivity : AppCompatActivity() {

    private var dataList = ArrayList<Wallet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wallet)
        var transaction: RecyclerView = findViewById(R.id.view_transaction)
        var topup: Button = findViewById(R.id.btn_topup)

        dataList.add(
            Wallet(
                "Eternals",
                "Sat, 12 Nov 2021",
                35000.0,
                "0"
            )
        )
        dataList.add(
            Wallet(
                "Eternals",
                "Sat, 10 Nov 2021",
                100000.0,
                "1"
            )
        )
        transaction.layoutManager = LinearLayoutManager(this)
        transaction.adapter = WalletAdapter(dataList){

        }
        topup.setOnClickListener {
            startActivity(Intent(this, TopUpActivity::class.java))
        }

    }
}