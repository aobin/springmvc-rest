package com.aobin.entity;

import com.ernieyu.feedparser.Item;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class FeedItem implements Serializable
{
    private String title;
    private String link;
    private String description;
    private Date pubDate;
    private String author;
    private String guid;
    private List<String> categories;

    public FeedItem(Item item)
    {
        this.title = item.getTitle();
        this.link = item.getLink();
        this.description = item.getDescription();
        this.pubDate = item.getPubDate();
        this.author = item.getAuthor();
        this.guid = item.getGuid();
        this.categories = item.getCategories();
    }

    public String getTitle()
    {
        return title;
    }

    public String getLink()
    {
        return link;
    }

    public String getDescription()
    {
        return description;
    }

    public Date getPubDate()
    {
        return pubDate;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getGuid()
    {
        return guid;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setPubDate(Date pubDate)
    {
        this.pubDate = pubDate;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public void setGuid(String guid)
    {
        this.guid = guid;
    }

    public void setCategories(List<String> categories)
    {
        this.categories = categories;
    }

    public List<String> getCategories()
    {
        return categories;

    }
}
