package com.project.movapps.home.ticket

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.project.movapps.R
import com.project.movapps.home.dashboard.ComingSoonAdapter
import com.project.movapps.model.Film
import com.project.movapps.util.Preferences

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TicketFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TicketFragment : Fragment() {
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
        return inflater.inflate(R.layout.fragment_ticket, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        preferences = Preferences(requireContext())
        mDatabase =
            FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Film")

        var listticket: RecyclerView? = view?.findViewById(R.id.rv_ticketlist)

        listticket?.layoutManager = LinearLayoutManager(context)
        getData()

    }

    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (getDataSnapshot in snapshot.children) {
                    val film = getDataSnapshot.getValue(Film::class.java)
                    dataList.add(film!!)
                }
                var listticket: RecyclerView? = view?.findViewById(R.id.rv_ticketlist)
                var totalticket: TextView? = view?.findViewById(R.id.txt_total_movie)

                listticket?.adapter = ComingSoonAdapter(dataList) {
                    var intent = Intent(context, TicketActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
                totalticket?.setText("${dataList.size} Movies")
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "" + error.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TicketFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TicketFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}