package com.entigrity.model.view_topics_of_interest;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopicOfInterestsItem implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private int id;

    @SerializedName("tags")
    private List<TagsItem> tags;

    public TopicOfInterestsItem(Parcel in) {
        this.name = in.readString();
        this.id = in.readInt();
        this.tags = in.createTypedArrayList(TagsItem.CREATOR);

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeTypedList(this.tags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TopicOfInterestsItem> CREATOR = new Creator<TopicOfInterestsItem>() {
        @Override
        public TopicOfInterestsItem createFromParcel(Parcel in) {
            return new TopicOfInterestsItem(in);
        }

        @Override
        public TopicOfInterestsItem[] newArray(int size) {
            return new TopicOfInterestsItem[size];
        }
    };

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTags(List<TagsItem> tags) {
        this.tags = tags;
    }

    public List<TagsItem> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return
                "TopicOfInterestsItem{" +
                        "name = '" + name + '\'' +
                        ",id = '" + id + '\'' +
                        ",tags = '" + tags + '\'' +
                        "}";
    }
}