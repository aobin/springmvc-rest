package com.aobin.service;

import com.ernieyu.feedparser.Feed;
import com.ernieyu.feedparser.FeedParser;
import com.ernieyu.feedparser.FeedParserFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class FeedParserService
{
    private final String url ="http://myprintly.com/feed/";
    private Feed feed;

    public InputStream getUrlAsInputStream()
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

    public Feed getFeed()
    {
        InputStream inputStream = getUrlAsInputStream();
        FeedParser parser = FeedParserFactory.newParser();
        try
        {
            this.feed = parser.parse(inputStream);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return feed;
    }


}
