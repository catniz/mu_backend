package com.musinsa.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Category {
    TOP("상의"),
    OUTER("아우터"),
    PANTS("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리"),
    UNKNOWN("-");

    @JsonProperty("name")
    private final String name = this.name();
    private final String koreanName;

    Category(String koreanName) {
        this.koreanName = koreanName;
    }

    @JsonCreator
    public static Category fromJson(@JsonProperty("name") String name) {
        try {
            return Category.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("unknown category. name: " + name);
        }
    }
}