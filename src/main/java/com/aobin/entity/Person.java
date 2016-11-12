package com.aobin.entity;

import java.io.Serializable;

public class Person implements Serializable
{
    public Person()
    {

    }

    private int id;
    private int age;

    public int getId()
    {
        return id;
    }

    public int getAge()
    {
        return age;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}
