package com.musinsa.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

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
    private final String name;
    private final String koreanName;

    Category(String koreanName) {
        this.name = super.name();
        this.koreanName = koreanName;
    }

    @JsonCreator
    public static Category fromJson(@JsonProperty("name") String name) {
        try {
            Category category = Category.valueOf(name.toUpperCase());
            if (category == Category.UNKNOWN) {
                throw new IllegalArgumentException("Invalid category: " + name);
            }
            return category;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("unknown category. name: " + name);
        }
    }

    private static final List<Category> VALID_CATEGORIES = Arrays.stream(Category.values()).filter(c -> c != Category.UNKNOWN).toList();
    public static List<Category> validValues() {
        return VALID_CATEGORIES;
    }
}