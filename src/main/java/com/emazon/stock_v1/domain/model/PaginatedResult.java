package com.emazon.stock_v1.domain.model;

import java.util.List;

public class PaginatedResult<T> {
    private List<T> items;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public PaginatedResult(List<T> items, int currentPage, int totalPages, long totalItems) {
        this.items = items;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
    }
}
