package com.example.mehak.notes_maker;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class KeyPhrase implements Parcelable{
    public String id;
    public ArrayList<String> res;

    public KeyPhrase(){}

    public KeyPhrase(Parcel in){
        id = in.readString();
        res = in.readArrayList(null);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeList(res);

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
