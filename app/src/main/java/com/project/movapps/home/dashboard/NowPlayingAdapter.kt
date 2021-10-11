package com.project.movapps.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.movapps.R
import com.project.movapps.model.Film

class NowPlayingAdapter(private var data: List<Film>, private val listener: (Film) -> Unit) :
    RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NowPlayingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflaterView = layoutInflater.inflate(R.layout.row_item_now_playing, parent, false)
        return ViewHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieTitle: TextView = view.findViewById(R.id.txt_title)
        private val movieGenre: TextView = view.findViewById(R.id.txtview_genre_movie)
        private val movieRating: TextView = view.findViewById(R.id.txtview_rating)

        private val moviePoster: ImageView = view.findViewById(R.id.img_poster)

        fun bindItem(data: Film, listener: (Film) -> Unit, context : Context, position : Int) {
            movieTitle.setText(data.judul)
            movieGenre.setText(data.genre)
            movieRating.setText(data.rating)

            Glide.with(context).load(data.poster).into(moviePoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}
