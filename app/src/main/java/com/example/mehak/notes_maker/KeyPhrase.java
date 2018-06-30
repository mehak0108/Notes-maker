package com.example.mehak.notes_maker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class KeyPhrase implements Parcelable{
    public String name;
    public String url;
    public String language;

    public KeyPhrase(){}

    public KeyPhrase(Parcel in){
        name = in.readString();
        //url = in.readArrayList(null);
        url = in.readString();
        language = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(language);
    }

    public static final Parcelable.Creator<KeyPhrase> CREATOR=new Parcelable.Creator<KeyPhrase>(){
        public KeyPhrase createFromParcel(Parcel parcel){
            return new KeyPhrase(parcel);
        }
        public KeyPhrase[] newArray(int i){
            return new KeyPhrase[i];
        }
    };
}
