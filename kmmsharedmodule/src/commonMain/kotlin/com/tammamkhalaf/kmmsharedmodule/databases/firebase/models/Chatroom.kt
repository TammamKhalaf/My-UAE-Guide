package com.tammamkhalaf.myuaeguide.databases.firebase.models

class Chatroom : android.os.Parcelable {
    private var chatroom_name: String? = null
    private var creator_id: String? = null
    private var security_level: String? = null
    private var chatroom_id: String? = null
    private var chatroom_messages: List<com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage>? = null

    constructor(chatroom_name: String?, creator_id: String?, security_level: String?, chatroom_id: String?, chatroom_messages: List<com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage>?) {
        this.chatroom_name = chatroom_name
        this.creator_id = creator_id
        this.security_level = security_level
        this.chatroom_id = chatroom_id
        this.chatroom_messages = chatroom_messages
    }

    constructor() {}
    protected constructor(`in`: android.os.Parcel) {
        chatroom_name = `in`.readString()
        creator_id = `in`.readString()
        security_level = `in`.readString()
        chatroom_id = `in`.readString()
    }

    fun getChatroom_messages(): List<com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage>? {
        return chatroom_messages
    }

    fun setChatroom_messages(chatroom_messages: List<com.tammamkhalaf.myuaeguide.databases.firebase.models.ChatMessage>?) {
        this.chatroom_messages = chatroom_messages
    }

    fun getChatroom_id(): String? {
        return chatroom_id
    }

    fun setChatroom_id(chatroom_id: String?) {
        this.chatroom_id = chatroom_id
    }

    fun getChatroom_name(): String? {
        return chatroom_name
    }

    fun setChatroom_name(chatroom_name: String?) {
        this.chatroom_name = chatroom_name
    }

    fun getCreator_id(): String? {
        return creator_id
    }

    fun setCreator_id(creator_id: String?) {
        this.creator_id = creator_id
    }

    fun getSecurity_level(): String? {
        return security_level
    }

    fun setSecurity_level(security_level: String?) {
        this.security_level = security_level
    }

    override fun toString(): String {
        return "Chatroom{" +
                "chatroom_name='" + chatroom_name + '\'' +
                ", creator_id='" + creator_id + '\'' +
                ", security_level='" + security_level + '\'' +
                ", chatroom_id='" + chatroom_id + '\'' +
                ", chatroom_messages=" + chatroom_messages +
                '}'
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: android.os.Parcel, i: Int) {
        parcel.writeString(chatroom_name)
        parcel.writeString(creator_id)
        parcel.writeString(security_level)
        parcel.writeString(chatroom_id)
    }

    companion object {
        val CREATOR: android.os.Parcelable.Creator<Chatroom> = object : android.os.Parcelable.Creator<Chatroom?>() {
            override fun createFromParcel(`in`: android.os.Parcel): Chatroom {
                return Chatroom(`in`)
            }

            override fun newArray(size: Int): Array<Chatroom> {
                return arrayOfNulls(size)
            }
        }
    }
}