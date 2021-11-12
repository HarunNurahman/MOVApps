package com.project.movapps.wallet.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.project.movapps.R
import com.project.movapps.wallet.model.Wallet
import org.w3c.dom.Text
import java.lang.NumberFormatException
import java.text.NumberFormat
import java.util.*

class WalletAdapter(private var data: List<Wallet>, private val listener: (Wallet) -> Unit) :
    RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    lateinit var contextAdapter: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WalletAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context
        val inflaterView = layoutInflater.inflate(R.layout.row_item_transaction, parent, false)
        return ViewHolder(inflaterView)
    }

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movie: TextView = view.findViewById(R.id.txt_movie)
        private val date: TextView = view.findViewById(R.id.txt_date)
        private val amount: TextView = view.findViewById(R.id.txt_amount)

        fun bindItem(data: Wallet, listener: (Wallet) -> Unit, context: Context, position: Int) {

            movie.setText(data.title)
            date.setText(data.date)

            val localID = Locale("in", "ID")
            val formatRp = NumberFormat.getCurrencyInstance(localID)

            if (data.status!!.equals("0")) {
                amount.setText("- "+formatRp.format(data.amount))
            } else {
                amount.setText("+ "+formatRp.format(data.amount))
                amount.setTextColor(Color.GREEN)
            }


            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}