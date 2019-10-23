package com.entigrity.model.view_topics_of_interest;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@SuppressLint("ParcelCreator")
public class TagsItem implements Parcelable {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	protected TagsItem(Parcel in) {
		name = in.readString();
		id = in.readInt();
	}

	public static final Creator<TagsItem> CREATOR = new Creator<TagsItem>() {
		@Override
		public TagsItem createFromParcel(Parcel in) {
			return new TagsItem(in);
		}

		@Override
		public TagsItem[] newArray(int size) {
			return new TagsItem[size];
		}
	};

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"TagsItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(id);
	}
}