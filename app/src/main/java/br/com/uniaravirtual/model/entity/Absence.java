package br.com.uniaravirtual.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Absence implements Parcelable {

    @Expose(deserialize = false)
    private Integer mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("frequency")
    private String mFrequency;

    @SerializedName("total")
    private String mQuantity;

    protected Absence(Parcel in) {
        mName = in.readString();
        mFrequency = in.readString();
        mQuantity = in.readString();
    }

    public Absence() { }

    public String getName() {
        return mName;
    }

    public String getFrequency() {
        return mFrequency;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setFrequency(String frequency) {
        mFrequency = frequency;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mFrequency);
        dest.writeString(mQuantity);
    }

    public static final Creator<Absence> CREATOR = new Creator<Absence>() {
        @Override
        public Absence createFromParcel(Parcel in) {
            return new Absence(in);
        }

        @Override
        public Absence[] newArray(int size) {
            return new Absence[size];
        }
    };

    @Override
    public String toString() {
        return "Absence{" +
                "mName='" + mName + '\'' +
                ", mFrequency='" + mFrequency + '\'' +
                ", mQuantity='" + mQuantity + '\'' +
                '}';
    }
}
