package com.project.movapps.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.project.movapps.R
import com.project.movapps.home.dashboard.DashboardFragment
import com.project.movapps.home.settings.ProfileFragment
import com.project.movapps.home.ticket.TicketFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()
        setFragment(fragmentHome)

        val menu = findViewById<ImageView>(R.id.menu_home)
        val ticket = findViewById<ImageView>(R.id.menu_ticket)
        val user = findViewById<ImageView>(R.id.menu_user)

        menu.setOnClickListener {
            setFragment(fragmentHome)
            changeIcon(menu, R.drawable.ic_home_active)
            changeIcon(ticket, R.drawable.ic_ticket)
            changeIcon(user, R.drawable.ic_profile)
        }

        ticket.setOnClickListener {
            setFragment(fragmentTicket)
            changeIcon(menu, R.drawable.ic_home)
            changeIcon(ticket, R.drawable.ic_ticket_active)
            changeIcon(user, R.drawable.ic_profile)
        }

        user.setOnClickListener {
            setFragment(fragmentProfile)
            changeIcon(menu, R.drawable.ic_home)
            changeIcon(ticket, R.drawable.ic_ticket)
            changeIcon(user, R.drawable.ic_profile_active)
        }
    }

    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layout_frame, fragment)
        fragmentTransaction.commit()
    }

    private fun changeIcon(imageView: ImageView, int: Int){
        imageView.setImageResource(int)
    }
}