package com.dhee.entity;

public class UrlEntity {
    String id;
    String url;

    String sqli;

    public String getSqli() {
        return sqli;
    }

    public void setSqli(String sqli) {
        this.sqli = sqli;
    }

    public String getXss() {
        return xss;
    }

    public void setXss(String xss) {
        this.xss = xss;
    }

    String xss;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

