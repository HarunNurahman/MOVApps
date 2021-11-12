package com.project.movapps.checkout.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.movapps.R
import com.project.movapps.checkout.model.Checkout
import java.text.NumberFormat
import java.util.*

class CheckoutAdapter(private var data: List<Checkout>, private val listener: (Checkout) -> Unit) :
    RecyclerView.Adapter<CheckoutAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflaterView = layoutInflater.inflate(R.layout.row_item_checkout, parent, false)
        return ViewHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val seats: TextView = view.findViewById(R.id.txt_item)
        private val itemprice: TextView = view.findViewById(R.id.txt_price)

        fun bindItem(
            data: Checkout,
            listener: (Checkout) -> Unit,
            context: Context,
            position: Int
        ) {
            val localID = Locale("id", "ID")
            val formatIDR = NumberFormat.getCurrencyInstance(localID)
            itemprice.setText(formatIDR.format(data.harga!!.toDouble()))

            if (data.kursi!!.startsWith("Total")) {
                seats.setText(data.kursi)
                seats.setCompoundDrawables(null, null, null, null)
            } else {
                seats.setText("Seat No. "+data.kursi)
            }

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}
