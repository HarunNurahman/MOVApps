package com.project.movapps.sign.signup

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.project.movapps.home.HomeActivity
import com.project.movapps.R
import com.project.movapps.sign.signin.User
import com.project.movapps.util.Preferences
import java.util.*

class Register_PhotoActivity : AppCompatActivity(), PermissionListener {

    var REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var user: User
    lateinit var storage: FirebaseStorage
    lateinit var storageReference: StorageReference
    lateinit var preferences: Preferences

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_photo)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance("gs://mov-apps-c31eb.appspot.com/")
        storageReference = storage.getReference()

        mFirebaseInstance =
            FirebaseDatabase.getInstance("https://mov-apps-c31eb-default-rtdb.asia-southeast1.firebasedatabase.app/")
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        val welcome = findViewById<TextView>(R.id.txt_welcomename)
        val photo = findViewById<ImageView>(R.id.btn_add)
        val imageprofile = findViewById<ImageView>(R.id.img_profile)
        val register = findViewById<Button>(R.id.btn_save)
        val uploadlater = findViewById<Button>(R.id.btn_upload_later)

        user = intent.getParcelableExtra("data")!!
        welcome.text = "" + user.name

        photo.setOnClickListener {
            if (statusAdd) {
                statusAdd = false
                register.visibility = View.INVISIBLE
                photo.setImageResource(R.drawable.ic_add)
                imageprofile.setImageResource(R.drawable.user_pic)
            } else {
                ImagePicker.with(this)
                    .cameraOnly()    //User can only capture image using Camera
                    .start()
            }
        }

        uploadlater.setOnClickListener {
            finishAffinity()

            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            if (filePath != null) {
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Uploading...")
                progressDialog.show()

                var ref = storageReference.child("images/" + UUID.randomUUID().toString())
                ref.putFile(filePath).addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Uploaded", Toast.LENGTH_LONG).show()

                    ref.downloadUrl.addOnSuccessListener {
                        saveToFirebase(it.toString())
                    }
//                    finishAffinity()
//                    var intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
                }.addOnFailureListener {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Upload Failed", Toast.LENGTH_LONG).show()
                }.addOnProgressListener { taskSnapshot ->
                    var progress =
                        100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    progressDialog.setMessage("Upload " + progress.toInt() + "%")
                }
            }
        }
    }

    private fun saveToFirebase(url: String) {
        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValues("name", user.name.toString())
                preferences.setValues("username", user.username.toString())
                preferences.setValues("balance", "")
                preferences.setValues("url", "")
                preferences.setValues("email", user.email.toString())
                preferences.setValues("status", "1")
                preferences.setValues("url", url)

                finishAffinity()
                val intent = Intent(this@Register_PhotoActivity, HomeActivity::class.java)
                startActivity(intent)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Register_PhotoActivity, "" + error, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            takePictureIntent.resolveActivity(packageManager)?.also {
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//            }
//        }

        ImagePicker.with(this).cameraOnly().start()
    }

    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
        Toast.makeText(this, "You can't add profile picture", Toast.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        Toast.makeText(this, "Hurry? Click Upload Later", Toast.LENGTH_LONG).show()
    }

//    @SuppressLint("MissingSuperCall")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
//            var bitmap = data?.extras?.get("data") as Bitmap
//            statusAdd = true
//
//            filePath = data.data!!
//            val profile = findViewById<ImageView>(R.id.img_profile)
//            val register = findViewById<Button>(R.id.btn_register)
//            val add = findViewById<ImageView>(R.id.btn_add)
//            Glide.with(this).load(bitmap).apply(RequestOptions.circleCropTransform()).into(profile)
//
//            register.visibility = View.VISIBLE
//            add.setImageResource(R.drawable.ic_delete)
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val profile = findViewById<ImageView>(R.id.img_profile)
            val register = findViewById<Button>(R.id.btn_save)
            val add = findViewById<ImageView>(R.id.btn_add)

            statusAdd = true
            filePath = data?.data!!
            Glide.with(this).load(filePath).apply(RequestOptions.circleCropTransform())
                .into(profile)

            register.visibility = View.VISIBLE
            add.setImageResource(R.drawable.ic_delete)

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPermissionRationaleShouldBeShown(
        permission: PermissionRequest?,
        token: PermissionToken?
    ) {
        TODO("Not yet implemented")
    }
}