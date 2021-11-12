package com.project.movapps.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.project.movapps.MovieDetailActivity
import com.project.movapps.R
import com.project.movapps.checkout.model.Checkout
import com.project.movapps.home.model.Film

class ChooseSeatActivity : AppCompatActivity() {

    var statusA3: Boolean = false
    var statusA4: Boolean = false
    var total: Int = 0

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_seat)

        val title: TextView = findViewById(R.id.txt_movie_seat)
        val back: ImageView = findViewById(R.id.btn_back)
        val buyticket: Button = findViewById(R.id.btn_buy_ticket)

        val seatA3: ImageView = findViewById(R.id.a3)
        val seatA4: ImageView = findViewById(R.id.a4)

        back.setOnClickListener {
            var intent = Intent(this, MovieDetailActivity::class.java)
            startActivity(intent)
        }

        val data = intent.getParcelableExtra<Film>("data")
        title.text = data?.judul

        seatA3.setOnClickListener {
            if (statusA3) {
                seatA3.setImageResource(R.drawable.shape_empty_seat)
                statusA3 = false
                total -= 1
                buyTicket(total)
            } else {
                seatA3.setImageResource(R.drawable.ic_selected_seat)
                statusA3 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }

        seatA4.setOnClickListener {
            if (statusA4) {
                seatA4.setImageResource(R.drawable.shape_empty_seat)
                statusA4 = false
                total -= 1
                buyTicket(total)
            } else {
                seatA4.setImageResource(R.drawable.ic_selected_seat)
                statusA4 = true
                total += 1
                buyTicket(total)

                val data = Checkout("A3", "70000")
                dataList.add(data)
            }
        }

        buyticket.setOnClickListener {
            var intent = Intent(this, CheckoutActivity::class.java).putExtra("data", dataList).putExtra("datas", data)
            startActivity(intent)
        }
    }

    private fun buyTicket(total: Int) {
        val buy: Button = findViewById(R.id.btn_buy_ticket)

        if (total == 0){
            buy.setText("Buy Ticket(s)")
            buy.visibility = View.INVISIBLE
        } else {
            buy.setText("Buy Ticket ("+total+")")
            buy.visibility = View.VISIBLE
        }
    }
}