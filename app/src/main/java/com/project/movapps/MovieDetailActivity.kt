package com.project.movapps

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.project.movapps.checkout.ChooseSeatActivity
import com.project.movapps.home.dashboard.PlayAdapter
import com.project.movapps.home.model.Film
import com.project.movapps.home.model.Play

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var mDatabase: DatabaseReference
    private var dataList = ArrayList<Play>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val data = intent.getParcelableExtra<Film>("data")
        mDatabase = FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Film")
            .child(data?.judul.toString()).child("play")

        var title: TextView = findViewById(R.id.txtview_movie_title)
        var genre: TextView = findViewById(R.id.txtview_genre)
        var story: TextView = findViewById(R.id.txtview_movie_desc)
        var rating: TextView = findViewById(R.id.txtview_rating_movie)
        var cover: ImageView = findViewById(R.id.img_cover)
        var act: RecyclerView = findViewById(R.id.view_actor_actress)
        var seat: Button = findViewById(R.id.btn_choose_seat)

        title.text = data?.judul
        genre.text = data?.genre
        story.text = data?.desc
        rating.text = data?.rating

        Glide.with(this).load(data?.poster).into(cover)

        act.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        getData()

        seat.setOnClickListener{
            var intent = Intent(this@MovieDetailActivity, ChooseSeatActivity::class.java).putExtra("data", data)
            startActivity(intent)
        }
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var act: RecyclerView = findViewById(R.id.view_actor_actress)
                dataList.clear()

                for (getDataSnapshot in snapshot.children){
                    var Film = getDataSnapshot.getValue(Play::class.java)
                    dataList.add(Film!!)
                }

                act.adapter = PlayAdapter(dataList){

                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@MovieDetailActivity, ""+p0.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}