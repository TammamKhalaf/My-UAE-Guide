package com.tammamkhalaf.myuaeguide.categories.openTripMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Places {

    @SerializedName("kinds")
    @Expose
    private String kinds;
    @SerializedName("sources")
    @Expose
    private Sources sources;
    @SerializedName("bbox")
    @Expose
    private Bbox bbox;
    @SerializedName("point")
    @Expose
    private Point point;
    @SerializedName("osm")
    @Expose
    private String osm;
    @SerializedName("otm")
    @Expose
    private String otm;
    @SerializedName("xid")
    @Expose
    private String xid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("wikipedia")
    @Expose
    private String wikipedia;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("wikidata")
    @Expose
    private String wikidata;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("info")
    @Expose
    private Info info;

    public String getKinds() {
        return kinds;
    }

    public void setKinds(String kinds) {
        this.kinds = kinds;
    }

    public Sources getSources() {
        return sources;
    }

    public void setSources(Sources sources) {
        this.sources = sources;
    }

    public Bbox getBbox() {
        return bbox;
    }

    public void setBbox(Bbox bbox) {
        this.bbox = bbox;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getOsm() {
        return osm;
    }

    public void setOsm(String osm) {
        this.osm = osm;
    }

    public String getOtm() {
        return otm;
    }

    public void setOtm(String otm) {
        this.otm = otm;
    }

    public String getXid() {
        return xid;
    }

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}