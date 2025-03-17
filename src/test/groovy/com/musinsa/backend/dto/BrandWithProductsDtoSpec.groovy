package com.musinsa.backend.dto

import com.musinsa.backend.model.Category
import spock.lang.Specification

class BrandWithProductsDtoSpec extends Specification {
    def "DistinctProductsByCategory"() {
        given:
        def top1 = createProductDto(1, "A", Category.TOP, 10000)
        def top2 = createProductDto(2, "A", Category.TOP, 9000)
        def top3 = createProductDto(3, "A", Category.TOP, 11000)
        def outer = createProductDto(4, "A", Category.OUTER, 1000)
        def dto = new BrandWithProductsDto(1, "A", [top1, top2, top3, outer])

        when:
        def result = dto.distinctProductsByCategory(BrandWithProductsDto.selectCheapestProduct())

        then:
        result.id == 1
        result.name == "A"
        result.products.collect { [it.category, it.price] }.toSet() == [top2, outer].collect { [it.category, it.price] }.toSet()

    }

    private static ProductResponseDto createProductDto(long id, String brandName, Category category, int price) {
        return new ProductResponseDto(id, new BrandResponseDto(0, brandName), category, price)
    }
}
