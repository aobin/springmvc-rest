package com.aobin.entity;

public class Meta
{
    private int limit;
    private String next;
    private String previous;
    private int totalCount;
    private int offset;

    public Meta()
    {

    }

    public Meta(int limit, int offset, int totalCount)
    {
        this.limit = limit;
        this.offset = offset;
        this.totalCount = totalCount;
        this.setNext();
        this.setPrevious();
    }

    public int getOffset()
    {
        return offset;
    }

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public String getNext()
    {
        return this.next;
    }

    public void setNext()
    {
        int nextOffset = this.offset + this.limit;
        this.next = String.format("?limit=%d&offset=%d", this.limit, nextOffset);
    }

    public String getPrevious()
    {
        return this.previous;
    }

    public void setPrevious()
    {
        int previousOffset = (offset - limit < 1) ? 0 : offset - limit;
        this.previous = String.format("?limit=%d&offset=%d", this.limit, previousOffset);
    }

    public int getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(int totalCount)
    {
        this.totalCount = totalCount;
    }
}
