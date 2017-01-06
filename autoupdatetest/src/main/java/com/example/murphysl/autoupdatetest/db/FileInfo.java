package com.example.murphysl.autoupdatetest.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "FILE_INFO".
 */
@Entity
public class FileInfo {

    @NotNull
    private String filename;

    @NotNull
    private String my_url;
    private int length;
    private int progress;

    @Generated
    public FileInfo() {
    }

    @Generated
    public FileInfo(String filename, String my_url, int length, int progress) {
        this.filename = filename;
        this.my_url = my_url;
        this.length = length;
        this.progress = progress;
    }

    @NotNull
    public String getFilename() {
        return filename;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setFilename(@NotNull String filename) {
        this.filename = filename;
    }

    @NotNull
    public String getMy_url() {
        return my_url;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setMy_url(@NotNull String my_url) {
        this.my_url = my_url;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

}
