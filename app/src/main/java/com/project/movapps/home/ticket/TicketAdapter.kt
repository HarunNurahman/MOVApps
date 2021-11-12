package com.project.movapps.home.ticket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.movapps.R
import com.project.movapps.checkout.model.Checkout

class TicketAdapter(private var data: List<Checkout>, private val listener: (Checkout) -> Unit) :
    RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TicketAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflaterView = layoutInflater.inflate(R.layout.row_item_checkout_white, parent, false)
        return ViewHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val seats: TextView = view.findViewById(R.id.txt_item)

        fun bindItem(
            data: Checkout, listener: (Checkout) -> Unit, ) {
            seats.setText("Seat "+data.kursi)


            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}
