package com.tammamkhalaf.myuaeguide.databases.firebase.models

class ChatMessage {
    private var message: String? = null
    private var user_id: String? = null
    private var timestamp: String? = null
    private var profile_image: String? = null
    private var name: String? = null

    constructor(message: String?, user_id: String?, timestamp: String?, profile_image: String?, name: String?) {
        this.message = message
        this.user_id = user_id
        this.timestamp = timestamp
        this.profile_image = profile_image
        this.name = name
    }

    constructor() {}

    fun getProfile_image(): String? {
        return profile_image
    }

    fun setProfile_image(profile_image: String?) {
        this.profile_image = profile_image
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getUser_id(): String? {
        return user_id
    }

    fun setUser_id(user_id: String?) {
        this.user_id = user_id
    }

    fun getTimestamp(): String? {
        return timestamp
    }

    fun setTimestamp(timestamp: String?) {
        this.timestamp = timestamp
    }

    override fun toString(): String {
        return "ChatMessage{" +
                "message='" + message + '\'' +
                ", user_id='" + user_id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", profile_image='" + profile_image + '\'' +
                ", name='" + name + '\'' +
                '}'
    }
}