package com.androidapps.swathi.communityhelpapp;

/**
 * Created by Swathi on 5/28/2016.
 */
public class UserRequest {

    private String requestorName;
    private String activity;
    private String message;
    private String requestStatus;
    private int numberOfRequests;

    public int getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(int numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
