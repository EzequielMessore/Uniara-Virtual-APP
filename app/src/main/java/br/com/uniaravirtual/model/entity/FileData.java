package br.com.uniaravirtual.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileData implements Parcelable {

    public static final Parcelable.Creator<FileData> CREATOR = new Parcelable.Creator<FileData>() {
        public FileData createFromParcel(Parcel source) {
            return new FileData(source);
        }

        public FileData[] newArray(int size) {
            return new FileData[size];
        }
    };

    @Expose(deserialize = false)
    private Integer mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("link")
    private String mLink;

    @Expose(deserialize = false)
    private long mIdStudentFile;

    public FileData() {
    }

    protected FileData(Parcel in) {
        this.mName = in.readString();
        this.mLink = in.readString();
    }

    public long getIdStudentFile() {
        return mIdStudentFile;
    }

    public void setIdStudentFile(long idStudentFile) {
        mIdStudentFile = idStudentFile;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mLink);
    }

    @Override
    public String toString() {
        return getName();
    }

}
