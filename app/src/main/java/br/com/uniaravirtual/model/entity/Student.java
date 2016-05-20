package br.com.uniaravirtual.model.entity;

import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("name")
    private String mName;
    @SerializedName("ra")
    private String mRegisterStudent;
    @SerializedName("password")
    private String mPassword;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRegisterStudent() {
        return mRegisterStudent;
    }

    public void setRegisterStudent(String registerStudent) {
        mRegisterStudent = registerStudent;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
