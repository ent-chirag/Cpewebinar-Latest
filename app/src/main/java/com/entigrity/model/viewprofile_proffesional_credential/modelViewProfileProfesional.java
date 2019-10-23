package com.entigrity.model.viewprofile_proffesional_credential;

import android.os.Parcel;
import android.os.Parcelable;

public class modelViewProfileProfesional implements Parcelable {

    public String name = "";

    public modelViewProfileProfesional(String name, int id) {
        this.name = name;
        this.id = id;
    }

    protected modelViewProfileProfesional(Parcel in) {
        name = in.readString();
        id = in.readInt();

    }

    public static final Creator<modelViewProfileProfesional> CREATOR = new Creator<modelViewProfileProfesional>() {
        @Override
        public modelViewProfileProfesional createFromParcel(Parcel in) {
            return new modelViewProfileProfesional(in);
        }

        @Override
        public modelViewProfileProfesional[] newArray(int size) {
            return new modelViewProfileProfesional[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int id = 0;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }

    @Override
    public String toString() {
        return
                "TopicOfInterestsItem{" +
                        "name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}
