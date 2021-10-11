package com.project.movapps.home.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import com.project.movapps.MovieDetailActivity
import com.project.movapps.R
import com.project.movapps.home.dashboard.ComingSoonAdapter
import com.project.movapps.home.dashboard.NowPlayingAdapter
import com.project.movapps.model.Film
import com.project.movapps.util.Preferences
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var preferences: Preferences
    private lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferences = Preferences(requireActivity().applicationContext)
        mDatabase =
            FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film")

        val user = view?.findViewById<TextView>(R.id.txtview_user)
        val balance = view?.findViewById<TextView>(R.id.txtview_balance)
        val profile = view?.findViewById<ImageView>(R.id.img_profile_pic)
        val nowplaying = view?.findViewById<RecyclerView>(R.id.view_now_playing)
        val comingsoon = view?.findViewById<RecyclerView>(R.id.view_coming_soon)

        user?.setText(preferences.getValues("name"))
        if (!preferences.getValues("balance").equals("")) {
            currency(preferences.getValues("balance")!!.toDouble(), balance!!)
        }

        Glide.with(this).load(preferences.getValues("url"))
            .apply(RequestOptions.circleCropTransform()).into(profile!!)

        nowplaying?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        comingsoon?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        getData()
    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in dataSnapshot.children){
                    var film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }
                val nowplaying = view?.findViewById<RecyclerView>(R.id.view_now_playing)
                val comingsoon = view?.findViewById<RecyclerView>(R.id.view_coming_soon)

                nowplaying?.adapter = NowPlayingAdapter(dataList){
                    var intent = Intent(context, MovieDetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }

                comingsoon?.adapter = ComingSoonAdapter(dataList){
                    var intent = Intent(context, MovieDetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(context, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun currency(price: Double, textview: TextView) {
        val localID = Locale("in", "ID")
        val format = NumberFormat.getCurrencyInstance(localID)
        textview.setText(format.format(price))

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}