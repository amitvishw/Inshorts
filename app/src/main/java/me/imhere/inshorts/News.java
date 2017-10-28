package me.imhere.inshorts;

import java.io.Serializable;
import java.sql.Timestamp;

class News implements Serializable
{
    private int id;
    private String title;
    private String url;
    private String publisher;
    private String hostname;
    private String catgory;
    private Timestamp timestamp;

    public News(int id, String title, String url, String publisher, String hostname, String catgory, Timestamp timestamp) {
        this.id          = id;
        this.title       = title;
        this.url         = url;
        this.publisher   = publisher;
        this.hostname    = hostname;
        this.catgory     = catgory;
        this.timestamp   = timestamp;
    }

    News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getUrl() {
        return url;
    }

    void setUrl(String url) {
        this.url = url;
    }

    String getPublisher() {
        return publisher;
    }

    void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getHostname() {
        return hostname;
    }

    void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getCatgory() {
        return catgory;
    }

    void setCatgory(String catgory) {
        this.catgory = catgory;
    }

    Timestamp getTimestamp() {
        return timestamp;
    }

    void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
