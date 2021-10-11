    package com.project.movapps.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.movapps.R
import com.project.movapps.model.Play

    class PlayAdapter(private var data: List<Play>, private val listener: (Play) -> Unit) :
    RecyclerView.Adapter<PlayAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflaterView = layoutInflater.inflate(R.layout.row_item_play, parent, false)
        return ViewHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: PlayAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieTitle: TextView = view.findViewById(R.id.txt_title)

        private val moviePoster: ImageView = view.findViewById(R.id.img_poster)

        fun bindItem(data: Play, listener: (Play) -> Unit, context : Context, position : Int) {
            movieTitle.setText(data.nama)

            Glide.with(context).load(data.url).apply(RequestOptions.circleCropTransform()).into(moviePoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}