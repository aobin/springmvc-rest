package com.aobin.controller;

import com.aobin.entity.FeedDocument;
import com.aobin.entity.FeedItem;
import com.aobin.entity.Meta;
import com.aobin.entity.MetaItems;
import com.aobin.service.FeedParserService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

import java.util.List;
import java.util.concurrent.TimeoutException;


@Controller
public class MainController
{

    private FeedParserService feedParserService;

    private MemcachedClient memcachedClient;

    @Autowired
    public MainController(FeedParserService feedParserService, MemcachedClient memcachedClient)
    {
        this.feedParserService = feedParserService;
        this.memcachedClient = memcachedClient;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index()
    {
        return "index";
    }

    @RequestMapping(value = "/hello", produces = "text/plain;charset=UTF-8")
    public
    @ResponseBody
    String hello()
    {
        return "hello111";
    }

    @RequestMapping(value = "/say/{msg}", produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String say(@PathVariable(value = "msg") String msg)
    {
        return "{\"msg\":\"you say:'" + msg + "'\"}";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    FeedDocument get()
    {
        FeedDocument feedDocument = this.getFeedDocumentFromMemcache();

        return feedDocument;
    }

    @ControllerAdvice
    static class JsonpAdvice extends AbstractJsonpResponseBodyAdvice
    {
        public JsonpAdvice()
        {
            super("callback");
        }
    }

    @RequestMapping(value = "/feedItems", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    MetaItems getFeedItems(@RequestParam(value = "limit") int limit, @RequestParam(value = "offset") int offset)
    {
        FeedDocument feedDocument = this.getFeedDocumentFromMemcache();
        Meta meta = null;
        MetaItems metaItems = new MetaItems();
        if (feedDocument != null && feedDocument.getFeedItems() != null && feedDocument.getFeedItems().size() > 0)
        {
            meta = new Meta(limit, offset, feedDocument.getFeedItems().size());
        }
        metaItems.setMeta(meta);
        List<FeedItem> feedItems = feedDocument.getFeedItems().subList(offset,offset+limit);
        feedDocument.setFeedItems(feedItems);
        metaItems.setFeedDocument(feedDocument);

        return metaItems;
    }

    private FeedDocument getFeedDocumentFromMemcache()
    {
        FeedDocument feedDocument = null;
        try
        {
            feedDocument = memcachedClient.get("feedDocument");
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
        return feedDocument;
    }
}
