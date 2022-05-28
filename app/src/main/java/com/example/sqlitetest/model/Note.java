package com.example.sqlitetest.model;

public class Note {
    private int mID;
    private String mTitle;
    private String mContent;

    public Note(int mID, String mTitle, String mContent) {
        this.mID = mID;
        this.mTitle = mTitle;
        this.mContent = mContent;
    }

    public int getID() {
        return mID;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getContent() {
        return mContent;
    }
}
