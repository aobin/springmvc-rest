package com.aobin.controller;

import com.aobin.entity.FeedDocument;
import com.aobin.service.FeedParserService;
import com.ernieyu.feedparser.Feed;
import com.ernieyu.feedparser.Item;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Controller
public class MainController
{

    @Autowired
    private FeedParserService feedParserService;

    @Autowired
    private MemcachedClient memcachedClient;

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
        return "你好！hello111";
    }

    @RequestMapping(value = "/say/{msg}", produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String say(@PathVariable(value = "msg") String msg)
    {
        return "{\"msg\":\"you say:'" + msg + "'\"}";
    }

    @RequestMapping(value = "/itemList", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Item> getItemList()
    {
        InputStream urlAsInputStream = feedParserService.getUrlAsInputStream();
        System.out.println("input stream: " + urlAsInputStream.toString());
        Feed feed = feedParserService.getFeed();
        System.out.println("feed is : " + feed);
        System.out.println("feed description: " + feed.getDescription());

        List<Item> itemList = feed.getItemList();
        for (int i = 0; i < itemList.size(); i++)
        {
            System.out.println("======item[" + i + "] begin======");
            System.out.println("title:" + itemList.get(i).getTitle());
            System.out.println("link:" + itemList.get(i).getLink());
            System.out.println("Description:" + itemList.get(i).getDescription());
            System.out.println("Content:" + itemList.get(i).getContent());
            System.out.println("======item[" + i + "] end======");
        }

        return itemList;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public
    @ResponseBody
    String add()
    {
        List<Item> items = this.getItemList();
        FeedDocument feedDocument = new FeedDocument(items);
        try
        {
            memcachedClient.add("feedDocument", 0, feedDocument);
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
        return "success";
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    FeedDocument get()
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
