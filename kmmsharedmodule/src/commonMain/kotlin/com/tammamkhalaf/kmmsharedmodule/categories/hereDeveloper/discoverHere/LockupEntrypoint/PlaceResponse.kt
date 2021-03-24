package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint

import androidx.room.Entity

@Entity(tableName = "PlacesTable")
class PlaceResponse {
    @Expose(deserialize = false, serialize = false)
    @PrimaryKey(autoGenerate = true)
    private var id = 0

    @SerializedName("name")
    @Expose
    private var name: String? = null

    @SerializedName("placeId")
    @Expose
    private var placeId: String? = null

    @SerializedName("view")
    @Expose
    private var view: String? = null

    @SerializedName("icon")
    @Expose
    private var icon: String? = null

    @Ignore
    @SerializedName("location")
    @Expose
    private var location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Location? = null

    @Ignore
    @SerializedName("contacts")
    @Expose
    private var contacts: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Contacts? = null

    @Ignore
    @SerializedName("categories")
    @Expose
    private var categories: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Category>? = null

    @Ignore
    @SerializedName("media")
    @Expose
    private var media: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Media? = null

    @SerializedName("extended")
    @Expose
    @Ignore
    private var extended: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Extended? = null

    @Ignore
    @SerializedName("related")
    @Expose
    private var related: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Related? = null

    constructor(name: String?, placeId: String?, view: String?, location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Location?, contacts: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Contacts?,
                categories: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Category>?, icon: String?, media: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Media?, extended: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Extended?, related: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Related?) {
        this.name = name
        this.placeId = placeId
        this.view = view
        this.location = location
        this.contacts = contacts
        this.categories = categories
        this.icon = icon
        this.media = media
        this.extended = extended
        this.related = related
    }

    constructor() {}

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getPlaceId(): String? {
        return placeId
    }

    fun setPlaceId(placeId: String?) {
        this.placeId = placeId
    }

    fun getView(): String? {
        return view
    }

    fun setView(view: String?) {
        this.view = view
    }

    fun getLocation(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Location? {
        return location
    }

    fun setLocation(location: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Location?) {
        this.location = location
    }

    fun getContacts(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Contacts? {
        return contacts
    }

    fun setContacts(contacts: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Contacts?) {
        this.contacts = contacts
    }

    fun getCategories(): List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Category>? {
        return categories
    }

    fun setCategories(categories: List<com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Category>?) {
        this.categories = categories
    }

    fun getIcon(): String? {
        return icon
    }

    fun setIcon(icon: String?) {
        this.icon = icon
    }

    fun getMedia(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Media? {
        return media
    }

    fun setMedia(media: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Media?) {
        this.media = media
    }

    fun getExtended(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Extended? {
        return extended
    }

    fun setExtended(extended: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Extended?) {
        this.extended = extended
    }

    fun getRelated(): com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Related? {
        return related
    }

    fun setRelated(related: com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint.Related?) {
        this.related = related
    }

    fun getId(): Int {
        return id
    }
}