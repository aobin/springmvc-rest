package com.aobin.service;

import com.ernieyu.feedparser.Feed;
import org.springframework.stereotype.Service;

@Service
public interface FeedParserService
{
    public Feed getFeed();


}
