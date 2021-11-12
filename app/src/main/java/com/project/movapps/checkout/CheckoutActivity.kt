package com.project.movapps.checkout

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.movapps.R
import com.project.movapps.checkout.adapter.CheckoutAdapter
import com.project.movapps.home.ticket.TicketActivity
import com.project.movapps.checkout.model.Checkout
import com.project.movapps.home.model.Film
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
        var cancel: Button = findViewById(R.id.btn_cancel_payment)
        var payment: Button = findViewById(R.id.btn_confirm_payment)
        val data = intent.getParcelableExtra<Film>("datas")

        for (a in dataList.indices) {
            total += dataList[a].harga!!.toInt()
        }

        dataList.add(Checkout("Total Payment", total.toString()))

        payment.setOnClickListener {
            var intent = Intent(this, CheckoutSuccessActivity::class.java)
            startActivity(intent)

            showNotification(data!!)
        }

        cancel.setOnClickListener {
            finish()
        }


        item.layoutManager = LinearLayoutManager(this)
        item.adapter = CheckoutAdapter(dataList) {

        }

    }

    private fun showNotification(datas: Film) {
        val NOTIFICATION_CHANNEL_ID = "notif_mov"
        val context = this.applicationContext
        var notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "MOV Notification Channel"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val mChannel = NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, importance)
            notificationManager.createNotificationChannel(mChannel)
        }

//        val intent = Intent(this@CheckoutActivity, CheckoutSuccessActivity::class.java)
//        val bundle = Bundle()
//        bundle.putString("id", "id_film")
//        intent.putExtras(bundle)

        val intent = Intent(this, TicketActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable("data", datas)
        intent.putExtras(bundle)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        val builder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
        builder.setContentIntent(pendingIntent).setSmallIcon(R.drawable.logo_mov)
            .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                R.drawable.logo_mov_notification
            ))
            .setTicker("Notification Starting")
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setDefaults(Notification.DEFAULT_SOUND)
            .setContentTitle("Payment Successful")
            .setContentText("Ticket "+datas.judul+" Has Been Successfully Purchased. Enjoy Your Movie!")

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(115, builder.build())
    }
}