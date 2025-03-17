package com.musinsa.backend.controller;

import com.musinsa.backend.dto.CategoryMinMaxPriceDto;
import com.musinsa.backend.dto.LowestPriceBrandDto;
import com.musinsa.backend.dto.LowestPriceProductsByCategoryDto;
import com.musinsa.backend.model.Category;
import com.musinsa.backend.service.PriceComparisonService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/price-comparison")
public class PriceComparisonController {

    private final PriceComparisonService priceComparisonService;

    @GetMapping("/lowest-by-all-category")
    public LowestPriceProductsByCategoryDto getLowestByAllCategory() {
        return priceComparisonService.getLowestByCategory(Category.validValues());
    }

    @GetMapping("/lowest-brand")
    public LowestPriceBrandDto getLowestBrand() {
        return priceComparisonService.getLowestBrand();
    }

    @GetMapping("/min-max")
    public CategoryMinMaxPriceDto getCategoryMinMaxPrice(@PathParam("category") Category category) {
        return priceComparisonService.getCategoryMinMaxPrice(category);
    }

}
