package com.aobin.controller;

import com.aobin.entity.FeedDocument;
import com.aobin.service.FeedParserService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
        return "你好！hello111";
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
