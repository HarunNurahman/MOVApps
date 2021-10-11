package com.project.movapps.sign.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.project.movapps.R

import com.project.movapps.home.HomeActivity
import com.project.movapps.sign.signup.RegisterActivity
import com.project.movapps.util.Preferences

class SignInActivity : AppCompatActivity() {

    lateinit var iUsername: String
    lateinit var iPassword: String

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        mDatabase =
            FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("User")
        preferences = Preferences(this)

        preferences.setValues("onboarding", "1")
        if (preferences.getValues("status").equals("1")) {
            finishAffinity()
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        val btn_signin = findViewById<Button>(R.id.btn_sign_in)
        val btn_register = findViewById<Button>(R.id.btn_register)
        val usern = findViewById<TextView>(R.id.txt_username)
        val passw = findViewById<TextView>(R.id.txt_pass)
        btn_signin.setOnClickListener {
            iUsername = usern.text.toString()
            iPassword = passw.text.toString()

            if (iUsername.equals("")) {
                usern.error = "Please enter your username"
                usern.requestFocus()
            } else if (iPassword.equals("")) {
                passw.error = "Please enter your password"
                passw.requestFocus()
            } else {
                var statusUsername = iUsername.indexOf(".")
                if (statusUsername >= 0) {
                    usern.error = "Silahkan tulis Username Anda tanpa ."
                    usern.requestFocus()
                } else {
                    pushLogin(iUsername, iPassword)
                }
            }
        }

        btn_register.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent);
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Toast.makeText(this@SignInActivity, "User Not Found!", Toast.LENGTH_LONG).show()
                } else {
                    if (user.password.equals(iPassword)) {
                        preferences.setValues("name", user.name.toString())
                        preferences.setValues("username", user.username.toString())
                        preferences.setValues("url", user.url.toString())
                        preferences.setValues("email", user.email.toString())
                        preferences.setValues("balance", user.balance.toString())
                        preferences.setValues("status", "1")

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@SignInActivity,
                            "Password Incorrect, Try Again",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignInActivity, "" + error.message, Toast.LENGTH_LONG).show()
            }
        })
    }
}