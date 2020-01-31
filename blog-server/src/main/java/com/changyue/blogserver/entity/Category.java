package com.changyue.blogserver.entity;

import lombok.Data;


public class Category {

    private Integer id;

    private String description;

    private String name;

    private Integer parentId;

    private String slugName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName == null ? null : slugName.trim();
    }
}
