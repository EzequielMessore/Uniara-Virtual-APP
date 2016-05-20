package br.com.uniaravirtual.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Grades implements Parcelable {

    @Expose(deserialize = false)
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("first_bimester")
    private String mFirstBimester;
    @SerializedName("second_bimester")
    private String mSecondBimester;
    @SerializedName("third_bimester")
    private String mThirdBimester;
    @SerializedName("fourth_bimester")
    private String mFourthBimester;
    @SerializedName("substitutive")
    private String mReplacement;
    @SerializedName("average")
    private String mAverage;
    @SerializedName("recovery")
    private String mRecovery;
    @SerializedName("status")
    private String mStatus;

    protected Grades(Parcel in) {
        mName = in.readString();
        mFirstBimester = in.readString();
        mSecondBimester = in.readString();
        mThirdBimester = in.readString();
        mFourthBimester = in.readString();
        mReplacement = in.readString();
        mAverage = in.readString();
        mRecovery = in.readString();
        mStatus = in.readString();
    }

    public Grades() {
    }

    public static final Creator<Grades> CREATOR = new Creator<Grades>() {
        @Override
        public Grades createFromParcel(Parcel in) {
            return new Grades(in);
        }

        @Override
        public Grades[] newArray(int size) {
            return new Grades[size];
        }
    };

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getFirstBimester() {
        return mFirstBimester;
    }

    public void setFirstBimester(String mFirstBimester) {
        this.mFirstBimester = mFirstBimester;
    }

    public String getSecondBimester() {
        return mSecondBimester;
    }

    public void setSecondBimester(String mSecondBimester) {
        this.mSecondBimester = mSecondBimester;
    }

    public String getThirdBimester() {
        return mThirdBimester;
    }

    public void setThirdBimester(String mThirdBimester) {
        this.mThirdBimester = mThirdBimester;
    }

    public String getFourthBimester() {
        return mFourthBimester;
    }

    public void setFourthBimester(String mFourthBimester) {
        this.mFourthBimester = mFourthBimester;
    }

    public String getReplacement() {
        return mReplacement;
    }

    public void setReplacement(String mReplacement) {
        this.mReplacement = mReplacement;
    }

    public String getAverage() {
        return mAverage;
    }

    public void setAverage(String mAverage) {
        this.mAverage = mAverage;
    }

    public String getRecovery() {
        return mRecovery;
    }

    public void setRecovery(String mRecovery) {
        this.mRecovery = mRecovery;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    @Override
    public String toString() {
        return "" +
                "Name='" + mName + '\n' +
                ", FirstBimester='" + mFirstBimester + '\n' +
                ", SecondBimester='" + mSecondBimester + '\n' +
                ", ThirdBimester='" + mThirdBimester + '\n' +
                ", FourthBimester='" + mFourthBimester;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mFirstBimester);
        dest.writeString(mSecondBimester);
        dest.writeString(mThirdBimester);
        dest.writeString(mFourthBimester);
        dest.writeString(mReplacement);
        dest.writeString(mAverage);
        dest.writeString(mRecovery);
        dest.writeString(mStatus);
    }
}
