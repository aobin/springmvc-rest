package com.aobin.service;

import com.aobin.entity.FeedDocument;
import com.ernieyu.feedparser.Feed;
import com.ernieyu.feedparser.FeedParser;
import com.ernieyu.feedparser.FeedParserFactory;
import com.ernieyu.feedparser.Item;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class FeedParseServiceImpl implements FeedParserService
{

    private final String url = "http://myprintly.com/feed/";
    private Feed feed;
    private MemcachedClient memcachedClient;

    @Autowired
    public FeedParseServiceImpl(MemcachedClient memcachedClient)
    {
        this.memcachedClient = memcachedClient;
        InputStream inputStream = getUrlAsInputStream();
        saveFeedDocumentInMemcache(inputStream);
    }

    private InputStream getUrlAsInputStream()
    {
        URL url = null;
        InputStream feedStream = null;
        try
        {
            url = new URL(this.url);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                feedStream = httpConnection.getInputStream();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return feedStream;
    }

    private void saveFeedDocumentInMemcache(InputStream inputStream)
    {
        FeedParser parser = FeedParserFactory.newParser();
        try
        {
            this.feed = parser.parse(inputStream);
            System.out.println("get feed document from url: " + this.feed);
            List<Item> items = this.getFeed().getItemList();
            FeedDocument feedDocument = new FeedDocument(items);
            try
            {
                memcachedClient.add("feedDocument", 0, feedDocument);
                System.out.println("feedDocument saved into memcache");
            }
            catch (TimeoutException e)
            {
                e.printStackTrace();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            catch (MemcachedException e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public Feed getFeed()
    {
        return feed;
    }
}
