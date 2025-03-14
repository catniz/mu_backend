package com.musinsa.backend.converter;

import com.musinsa.backend.model.Category;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {

    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return Category.UNKNOWN.name();
        }
        return category.name();
    }

    @Override
    public Category convertToEntityAttribute(String s) {
        try {
            return Category.valueOf(s);
        } catch (IllegalArgumentException | NullPointerException e) {
            return Category.UNKNOWN;
        }
    }
}
