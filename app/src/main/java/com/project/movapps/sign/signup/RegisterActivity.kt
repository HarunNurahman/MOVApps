package com.project.movapps.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.project.movapps.R
import com.project.movapps.sign.signin.SignInActivity
import com.project.movapps.sign.signin.User
import com.project.movapps.util.Preferences
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    lateinit var rUsername: String
    lateinit var rPass: String
    lateinit var rFullName: String
    lateinit var rEmail: String

    lateinit var mFirebaseDatabaseReference: DatabaseReference
    lateinit var mFirebaseInstances: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mFirebaseInstances = FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/")
        mDatabase = FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("User")
        mFirebaseDatabaseReference = mFirebaseInstances.getReference("User")

        val btn_continue_reg = findViewById<Button>(R.id.btn_register)
        val usern = findViewById<TextView>(R.id.txt_username)
        val pass = findViewById<TextView>(R.id.txt_pass)
        val fullname = findViewById<TextView>(R.id.txt_full_name)
        val email = findViewById<TextView>(R.id.txt_email)
        val back = findViewById<ImageView>(R.id.btn_back)
        btn_continue_reg.setOnClickListener {
            rUsername = usern.text.toString()
            rPass = pass.text.toString()
            rFullName = fullname.text.toString()
            rEmail = email.text.toString()

            if (rUsername.equals("")) {
                usern.error = "Please Enter Your Username"
                usern.requestFocus()
            } else if (rPass.equals("")) {
                pass.error = "Please Enter Your Password"
                pass.requestFocus()
            } else if (rFullName.equals("")) {
                fullname.error = "Please Enter Your Full Name"
                fullname.requestFocus()
            } else if (rEmail.equals("")) {
                email.error = "Please Enter Your Valid Email"
                email.requestFocus()
            } else {
                saveUser(rUsername, rPass, rFullName, rEmail)
            }
        }

        back.setOnClickListener {
            var intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
        }
    }

    private fun saveUser(rUsername: String, rPass: String, rFullName: String, rEmail: String) {
        val user = User()
        user.username = rUsername
        user.password = rPass
        user.name = rFullName
        user.email = rEmail

        if (rUsername != null) {
            checkingUsername(rUsername, user)
        }
    }

    private fun checkingUsername(rUsername: String, data: User) {
        mFirebaseDatabaseReference.child(rUsername)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val user = dataSnapshot.getValue(User::class.java)
                    if (user == null) {
                        mFirebaseDatabaseReference.child(rUsername).setValue(data)

                        val intent =
                            Intent(this@RegisterActivity, Register_PhotoActivity::class.java).putExtra("data", data?.name)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "User Already Used",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "" + databaseError.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
    }
}