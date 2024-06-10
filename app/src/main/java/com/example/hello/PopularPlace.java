package com.example.hello;

import android.os.Parcel;
import android.os.Parcelable;

public class PopularPlace implements Parcelable {
    private String name;
    private String image;
    private boolean isFavorite; // Add favorite flag

    public PopularPlace(String name, String image) {
        this.name = name;
        this.image = image;
        this.isFavorite = false; // Default to not favorite
    }

    protected PopularPlace(Parcel in) {
        name = in.readString();
        image = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public void toggleFavorite() {
        isFavorite = !isFavorite;
    }

    public static final Creator<PopularPlace> CREATOR = new Creator<PopularPlace>() {
        @Override
        public PopularPlace createFromParcel(Parcel in) {
            return new PopularPlace(in);
        }

        @Override
        public PopularPlace[] newArray(int size) {
            return new PopularPlace[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    // Implement Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
