package com.aobin.entity;

public class MetaItems
{
    private Meta meta;
    private FeedDocument feedDocument;

    public MetaItems()
    {

    }

    public Meta getMeta()
    {
        return meta;
    }

    public void setMeta(Meta meta)
    {
        if (meta == null)
        {
            meta = new Meta();
        }
        this.meta = meta;
    }

    public FeedDocument getFeedDocument()
    {
        return feedDocument;
    }

    public void setFeedDocument(FeedDocument feedDocument)
    {
        if (feedDocument == null)
        {
            feedDocument = new FeedDocument();
        }
        this.feedDocument = feedDocument;
    }
}
