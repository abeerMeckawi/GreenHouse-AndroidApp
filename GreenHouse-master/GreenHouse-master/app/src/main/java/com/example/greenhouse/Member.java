package com.example.greenhouse;

public class Member {

    private String id;
    private String Fdate;
    private String Ldate;
    private String Optimal;

    public Member() {
    }

    public Member(String id, String fdate, String ldate, String optimal) {
        this.id = id;
        Fdate = fdate;
        Ldate = ldate;
        Optimal = optimal;
    }

    public Member(String id, String fdate, String ldate) {
        this.id = id;
        Fdate = fdate;
        Ldate = ldate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFdate() {
        return Fdate;
    }

    public void setFdate(String fdate) {
        Fdate = fdate;
    }

    public String getLdate() {
        return Ldate;
    }

    public void setLdate(String ldate) {
        Ldate = ldate;
    }

    public String getOptimal() {
        return Optimal;
    }

    public void setOptimal(String optimal) {
        Optimal = optimal;
    }
}
