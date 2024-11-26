
package com.twonnews.testautomation.model;

public class MenuItem {
    private String id;
    private String url;
    private String text;

    public MenuItem(String id, String url, String text) {
        this.id = id;
        this.url = url;
        this.text = text;
    }

    public String getId() { return id; }
    public String getUrl() { return url; }
    public String getText() { return text; }
}