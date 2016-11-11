package com.aobin.controller;

import com.aobin.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController
{
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
        return "你好！hello";
    }

    @RequestMapping(value = "/say/{msg}", produces = "application/json;charset=UTF-8")
    public
    @ResponseBody
    String say(@PathVariable(value = "msg") String msg)
    {
        return "{\"msg\":\"you say:'" + msg + "'\"}";
    }

    @RequestMapping(value = "/person", method = RequestMethod.GET)
    public
    @ResponseBody
    Person getPerson()
    {
        Person person = new Person();
        person.setAge(30);
        person.setId(10);
        return person;
    }

}
