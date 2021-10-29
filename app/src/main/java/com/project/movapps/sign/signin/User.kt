package com.project.movapps.sign.signin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class User (
    var name:String ?="",
    var email:String ?="",
    var username:String ?="",
    var password:String ?="",
    var url:String ?="",
    var balance:String ?=""
) : Parcelable