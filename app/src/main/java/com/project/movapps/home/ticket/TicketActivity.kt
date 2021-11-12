package com.project.movapps.home.ticket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.movapps.R
import com.project.movapps.checkout.model.Checkout
import com.project.movapps.home.model.Film

class TicketActivity : AppCompatActivity() {

    private var dataList = ArrayList<Checkout>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket)

        var data = intent.getParcelableExtra<Film>("data")

        var ticket_movie: TextView = findViewById(R.id.txt_ticket_title)
        var ticket_genre: TextView = findViewById(R.id.txt_ticket_genre)
        var ticket_rating: TextView = findViewById(R.id.txt_ticket_rating)
        var ticket_poster: ImageView = findViewById(R.id.img_ticket_poster)
        var ticket_checkout: RecyclerView = findViewById(R.id.rv_ticket_item)

        ticket_movie.text = data?.judul
        ticket_genre.text = data?.genre
        ticket_rating.text = data?.rating

        Glide.with(this).load(data?.poster).into(ticket_poster)

        //ticket_checkout.layoutManager = LinearLayoutManager(this)
        ticket_checkout.apply {
            layoutManager = GridLayoutManager(context, 2)
        }

        dataList.add(Checkout("A2", ""))
        dataList.add(Checkout("A3", ""))
        dataList.add(Checkout("B1", ""))
        dataList.add(Checkout("B2", ""))
        dataList.add(Checkout("B3", ""))

        ticket_checkout.adapter = TicketAdapter(dataList){

        }
    }
}