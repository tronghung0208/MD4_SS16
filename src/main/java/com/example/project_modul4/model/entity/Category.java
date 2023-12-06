package com.example.project_modul4.model.entity;

public class Category {
    private int categoryId;
    private String categoryName;
    private Boolean status=true;

    public Category() {
    }

    public Category(int categoryId, String categoryName, Boolean status) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
