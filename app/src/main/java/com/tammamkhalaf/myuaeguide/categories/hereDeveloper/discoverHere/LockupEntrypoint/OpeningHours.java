
package com.tammamkhalaf.myuaeguide.categories.hereDeveloper.discoverHere.LockupEntrypoint;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("isOpen")
    @Expose
    private Boolean isOpen;
    @SerializedName("structured")
    @Expose
    private List<Structured> structured = null;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }

    public List<Structured> getStructured() {
        return structured;
    }

    public void setStructured(List<Structured> structured) {
        this.structured = structured;
    }

}
