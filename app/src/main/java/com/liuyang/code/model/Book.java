package com.liuyang.code.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Liuyang 2016/3/20.
 */
public class Book implements Parcelable {

    private String name;
    private long ISBN;

    public Book(String name, long ISBN) {
        this.name = name;
        this.ISBN = ISBN;
    }

    protected Book(Parcel in) {
        name = in.readString();
        ISBN = in.readLong();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeLong(ISBN);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("name:").append(name).append(" ISBN").append(ISBN).toString();
    }
}
