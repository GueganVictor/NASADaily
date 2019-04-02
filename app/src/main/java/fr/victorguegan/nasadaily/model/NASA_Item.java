
package fr.victorguegan.nasadaily.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NASA_Item implements Comparable<NASA_Item>, Parcelable {

    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("explanation")
    @Expose
    private String explanation;
    @SerializedName("hdurl")
    @Expose
    private String hdurl;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("service_version")
    @Expose
    private String serviceVersion;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("url")
    @Expose
    private String url;

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public void setHdurl(String hdurl) {
        this.hdurl = hdurl;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int compareTo(NASA_Item o) {
        String current[] = this.date.split("-");
        String compare[] = o.getDate().split("-");
        if (Integer.parseInt(current[0]) == Integer.parseInt(compare[0])) {
            if (Integer.parseInt(current[1]) == Integer.parseInt(compare[1])) {
                if (Integer.parseInt(current[2]) == Integer.parseInt(compare[2])) {
                    return 0;
                } else{
                    return Integer.compare(Integer.parseInt(current[2]), Integer.parseInt(compare[2]));
                }
            } else {
                return Integer.compare(Integer.parseInt(current[1]), Integer.parseInt(compare[1]));
            }
        }
        return Integer.compare(Integer.parseInt(current[0]), Integer.parseInt(compare[0]));

    }

    protected NASA_Item(Parcel in) {
        explanation = in.readString();
        title = in.readString();
        url = in.readString();
        mediaType = in.readString();
        date = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NASA_Item> CREATOR = new Parcelable.Creator<NASA_Item>() {
        @Override
        public NASA_Item createFromParcel(Parcel in) {
            return new NASA_Item(in);
        }

        @Override
        public NASA_Item[] newArray(int size) {
            return new NASA_Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(explanation);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeString(mediaType);
        dest.writeString(date);
    }
}