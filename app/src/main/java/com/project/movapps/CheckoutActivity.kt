package com.project.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.movapps.checkout.CheckoutSuccessActivity
import com.project.movapps.home.dashboard.CheckoutAdapter
import com.project.movapps.model.Checkout
import com.project.movapps.util.Preferences

class CheckoutActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()
    private var total: Int = 0
    private lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        preference = Preferences(this)
        dataList = intent.getSerializableExtra("data") as ArrayList<Checkout>
        var item: RecyclerView = findViewById(R.id.rv_items)

        var payment: Button = findViewById(R.id.btn_confirm_payment)

        for (a in dataList.indices){
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Payment", total.toString()))

        item.layoutManager = LinearLayoutManager(this)
        item.adapter = CheckoutAdapter(dataList){

        }

        payment.setOnClickListener {
            var intent = Intent(this, CheckoutSuccessActivity::class.java)
            startActivity(intent)
        }
    }
}