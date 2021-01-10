package com.tammamkhalaf.myuaeguide.databases

class UserHelperClass {
    var fullName: String? = null
    var username: String? = null
    var email: String? = null
    var password: String? = null
    var gender: String? = null
    var date: String? = null
    var phoneNo: String? = null

    constructor(fullName: String?, username: String?, email: String?, password: String?, gender: String?, date: String?, phoneNo: String?) {
        this.fullName = fullName
        this.username = username
        this.email = email
        this.password = password
        this.gender = gender
        this.date = date
        this.phoneNo = phoneNo
    }

    constructor() {}
}