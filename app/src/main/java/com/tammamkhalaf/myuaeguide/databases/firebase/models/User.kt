package com.tammamkhalaf.myuaeguide.databases.firebase.models

class User {
    var fullName: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var gender: String? = null
    var date: String? = null
    var phoneNo: String? = null
    var profile_image: String? =null


    constructor() {}

    constructor(fullName: String?, username: String?, email: String?, password: String?, gender:
    String?, date: String?, phoneNo: String?, profile_img: String) {
        this.fullName = fullName
        this.username = username
        this.email = email
        this.password = password
        this.gender = gender
        this.date = date
        this.phoneNo = phoneNo
        this.profile_image = profile_img
    }


}