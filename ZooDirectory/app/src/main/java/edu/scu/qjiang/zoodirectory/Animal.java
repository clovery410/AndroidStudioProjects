package edu.scu.qjiang.zoodirectory;

/**
 * Created by clover on 4/30/16.
 */
public class Animal {
    private String name;
    private Integer smallId;
    private Integer bigId;
    private String description;

    public Animal(String name, Integer smallId, Integer bigId, String description) {
        this.name = name;
        this.smallId = smallId;
        this.bigId = bigId;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public Integer getImageId() {
        return smallId;
    }

    public Integer getBigImageId() {
        return bigId;
    }

    public String getDescription() {
        return description;
    }
}
