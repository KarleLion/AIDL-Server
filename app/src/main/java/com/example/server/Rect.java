package com.example.server;

import android.os.Parcel;
import android.os.Parcelable;


public final class Rect implements Parcelable {
    public int left;
    public int top;
    public int right;
    public int bottom;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.left);
        dest.writeInt(this.top);
        dest.writeInt(this.right);
        dest.writeInt(this.bottom);
    }

    public void readFromParcel(Parcel source) {
        this.left = source.readInt();
        this.top = source.readInt();
        this.right = source.readInt();
        this.bottom = source.readInt();
    }

    public Rect(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    protected Rect(Parcel in) {
        this.left = in.readInt();
        this.top = in.readInt();
        this.right = in.readInt();
        this.bottom = in.readInt();
    }

    public static final Parcelable.Creator<Rect> CREATOR = new Parcelable.Creator<Rect>() {
        @Override
        public Rect createFromParcel(Parcel source) {
            return new Rect(source);
        }

        @Override
        public Rect[] newArray(int size) {
            return new Rect[size];
        }
    };
}
