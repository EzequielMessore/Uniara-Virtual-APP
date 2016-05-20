package br.com.uniaravirtual.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StudentFiles implements Parcelable {

    @SerializedName("grade")
    private String mGrade;
    @SerializedName("files")
    private List<FileData> mFiles;
    @Expose(deserialize = false)
    private Integer mId;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        this.mGrade = grade;
    }

    public List<FileData> getFiles() {
        return mFiles;
    }

    public void setFiles(List<FileData> files) {
        this.mFiles = files;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mGrade);
        dest.writeTypedList(mFiles);
    }

    public StudentFiles() {
    }

    protected StudentFiles(Parcel in) {
        this.mGrade = in.readString();
        this.mFiles = in.createTypedArrayList(FileData.CREATOR);
    }

    public static final Creator<StudentFiles> CREATOR = new Creator<StudentFiles>() {
        public StudentFiles createFromParcel(Parcel source) {
            return new StudentFiles(source);
        }

        public StudentFiles[] newArray(int size) {
            return new StudentFiles[size];
        }
    };
}
