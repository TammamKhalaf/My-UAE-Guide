package com.tammamkhalaf.myuaeguide.categories.openTripMap

import com.google.gson.annotations.Expose

class Info {
    @SerializedName("descr")
    @Expose
    private var descr: String? = null

    @SerializedName("image")
    @Expose
    private var image: String? = null

    @SerializedName("img_width")
    @Expose
    private var imgWidth: Int? = null

    @SerializedName("src")
    @Expose
    private var src: String? = null

    @SerializedName("src_id")
    @Expose
    private var srcId: Int? = null

    @SerializedName("img_height")
    @Expose
    private var imgHeight: Int? = null
    fun getDescr(): String? {
        return descr
    }

    fun setDescr(descr: String?) {
        this.descr = descr
    }

    fun getImage(): String? {
        return image
    }

    fun setImage(image: String?) {
        this.image = image
    }

    fun getImgWidth(): Int? {
        return imgWidth
    }

    fun setImgWidth(imgWidth: Int?) {
        this.imgWidth = imgWidth
    }

    fun getSrc(): String? {
        return src
    }

    fun setSrc(src: String?) {
        this.src = src
    }

    fun getSrcId(): Int? {
        return srcId
    }

    fun setSrcId(srcId: Int?) {
        this.srcId = srcId
    }

    fun getImgHeight(): Int? {
        return imgHeight
    }

    fun setImgHeight(imgHeight: Int?) {
        this.imgHeight = imgHeight
    }
}