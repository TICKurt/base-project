package com.example.core.response;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 * 
 * @author example
 * @param <T> 数据类型
 */
public class PageResult<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 总记录数
     */
    private long total;
    
    /**
     * 当前页码
     */
    private long current;
    
    /**
     * 每页记录数
     */
    private long size;
    
    /**
     * 总页数
     */
    private long pages;
    
    /**
     * 数据列表
     */
    private List<T> records;
    
    public PageResult() {
    }
    
    public PageResult(long total, long current, long size, List<T> records) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.records = records;
        this.pages = total == 0 ? 0 : (total - 1) / size + 1;
    }
    
    public long getTotal() {
        return total;
    }
    
    public void setTotal(long total) {
        this.total = total;
    }
    
    public long getCurrent() {
        return current;
    }
    
    public void setCurrent(long current) {
        this.current = current;
    }
    
    public long getSize() {
        return size;
    }
    
    public void setSize(long size) {
        this.size = size;
    }
    
    public long getPages() {
        return pages;
    }
    
    public void setPages(long pages) {
        this.pages = pages;
    }
    
    public List<T> getRecords() {
        return records;
    }
    
    public void setRecords(List<T> records) {
        this.records = records;
    }
    
    /**
     * 是否有上一页
     */
    public boolean hasPrevious() {
        return current > 1;
    }
    
    /**
     * 是否有下一页
     */
    public boolean hasNext() {
        return current < pages;
    }
} 