package com.tammamkhalaf.myuaeguide.categories.openTripMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Info {

    @SerializedName("descr")
    @Expose
    private String descr;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("img_width")
    @Expose
    private Integer imgWidth;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("src_id")
    @Expose
    private Integer srcId;
    @SerializedName("img_height")
    @Expose
    private Integer imgHeight;

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(Integer imgWidth) {
        this.imgWidth = imgWidth;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Integer getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(Integer imgHeight) {
        this.imgHeight = imgHeight;
    }

}