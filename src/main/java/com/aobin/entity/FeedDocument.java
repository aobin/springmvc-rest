package com.aobin.entity;

import com.ernieyu.feedparser.Item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FeedDocument implements Serializable
{
    private List<FeedItem> feedItems;

    public List<FeedItem> getFeedItems()
    {
        return feedItems;
    }

    public void setFeedItems(List<FeedItem> feedItems)
    {
        this.feedItems = feedItems;
    }

    public FeedDocument(List<Item> items)
    {
        this.feedItems = new ArrayList<FeedItem>();
        for (int i = 0; i < items.size(); i++)
        {
            FeedItem feedItem = new FeedItem(items.get(i));
            this.feedItems.add(feedItem);
        }
    }

    public FeedDocument()
    {

    }

}
