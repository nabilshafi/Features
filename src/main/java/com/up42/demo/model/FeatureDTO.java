package com.up42.demo.model;

public class FeatureDTO {

    String id;
    long timestamp;
    long beginViewingDate;
    long endViewingDate;
    String missionName;

    public FeatureDTO(String id, long timestamp, long beginViewingDate, long endViewingDate, String missionName) {
        this.id = id;
        this.timestamp = timestamp;
        this.beginViewingDate = beginViewingDate;
        this.endViewingDate = endViewingDate;
        this.missionName = missionName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getBeginViewingDate() {
        return beginViewingDate;
    }

    public void setBeginViewingDate(long beginViewingDate) {
        this.beginViewingDate = beginViewingDate;
    }

    public long getEndViewingDate() {
        return endViewingDate;
    }

    public void setEndViewingDate(long endViewingDate) {
        this.endViewingDate = endViewingDate;
    }

    public String getMissionName() {
        return missionName;
    }

    public void setMissionName(String missionName) {
        this.missionName = missionName;
    }


}
