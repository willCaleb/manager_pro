package com.project.pro.model.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
    @JsonProperty("link")
    private String link;

    @JsonProperty("name")
    private String name;

    @JsonProperty("deletehash")
    private String deletehash;

    @JsonProperty("in_gallery")
    private boolean in_gallery;

    @JsonProperty("ad_url")
    private String ad_url;

    @JsonProperty("ad_type")
    private int ad_type;

    @JsonProperty("tags")
    private List<String> tags;

    @JsonProperty("in_most_viral")
    private boolean in_most_viral;

    @JsonProperty("is_ad")
    private boolean is_ad;

    @JsonProperty("account_id")
    private int account_id;

    @JsonProperty("favorite")
    private boolean favorite;

    @JsonProperty("bandwidth")
    private int bandwidth;

    @JsonProperty("views")
    private int views;

    @JsonProperty("size")
    private int size;

    @JsonProperty("height")
    private int height;

    @JsonProperty("width")
    private int width;

    @JsonProperty("animated")
    private boolean animated;

    @JsonProperty("type")
    private String type;

    @JsonProperty("datetime")
    private int datetime;

    @JsonProperty("id")
    private String id;
}
