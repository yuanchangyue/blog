package com.changyue.blogserver.entity;

public class Tag {
    private Integer id;

    private String name;

    private String slugName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSlugName() {
        return slugName;
    }

    public void setSlugName(String slugName) {
        this.slugName = slugName == null ? null : slugName.trim();
    }
}