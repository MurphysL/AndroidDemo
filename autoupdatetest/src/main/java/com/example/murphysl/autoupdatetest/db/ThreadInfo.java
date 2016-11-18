package com.example.murphysl.autoupdatetest.db;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.

/**
 * Entity mapped to table "THREAD_INFO".
 */
@Entity
public class ThreadInfo {

    @NotNull
    private String location;
    private int start;
    private int end;
    private Boolean isFinish;

    @NotNull
    private String threadName;

    @Generated
    public ThreadInfo() {
    }

    @Generated
    public ThreadInfo(String location, int start, int end, Boolean isFinish, String threadName) {
        this.location = location;
        this.start = start;
        this.end = end;
        this.isFinish = isFinish;
        this.threadName = threadName;
    }

    @NotNull
    public String getLocation() {
        return location;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setLocation(@NotNull String location) {
        this.location = location;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Boolean getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(Boolean isFinish) {
        this.isFinish = isFinish;
    }

    @NotNull
    public String getThreadName() {
        return threadName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setThreadName(@NotNull String threadName) {
        this.threadName = threadName;
    }

}
