package com.reachmyschool.attendance;

/**
 * Created by Admin on 12/28/2016.
 */

public class Model_Attendance {

    private String atten_date;
    private String status;

    public Model_Attendance(String atten_date, String status) {
        this.atten_date = atten_date;
        this.status = status;
    }

    public String getAtten_date() {
        return atten_date;
    }

    public void setAtten_date(String atten_date) {
        this.atten_date = atten_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
