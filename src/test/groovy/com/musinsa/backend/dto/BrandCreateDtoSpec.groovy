package com.musinsa.backend.dto

import com.musinsa.backend.model.Category
import spock.lang.Specification

class BrandCreateDtoSpec extends Specification {
    def "validateHasAllCategoryProducts - normal"() {
        given:
        def dto = new BrandCreateDto(
                name: "A",
                products: [
                        new ProductCreateDto(category: Category.TOP, price: 11200),
                        new ProductCreateDto(category: Category.OUTER, price: 5500),
                        new ProductCreateDto(category: Category.PANTS, price: 4200),
                        new ProductCreateDto(category: Category.SNEAKERS, price: 9000),
                        new ProductCreateDto(category: Category.BAG, price: 2000),
                        new ProductCreateDto(category: Category.HAT, price: 1700),
                        new ProductCreateDto(category: Category.SOCKS, price: 1800),
                        new ProductCreateDto(category: Category.ACCESSORY, price: 2300)
                ]
        )

        when:
        dto.validateHasAllCategoryProducts()

        then:
        notThrown(Exception)
    }

    def "validateHasAllCategoryProducts - error"() {
        given:
        def dto = new BrandCreateDto(
                name: "A",
                products: [
                        new ProductCreateDto(category: Category.TOP, price: 11200),
                        new ProductCreateDto(category: Category.OUTER, price: 5500),
                        new ProductCreateDto(category: Category.PANTS, price: 4200),
                        new ProductCreateDto(category: Category.SNEAKERS, price: 9000),
                        new ProductCreateDto(category: Category.BAG, price: 2000),
                        new ProductCreateDto(category: Category.HAT, price: 1700),
                        new ProductCreateDto(category: Category.SOCKS, price: 1800),
                ]
        )

        when:
        dto.validateHasAllCategoryProducts()

        then:
        def e = thrown(IllegalArgumentException)
        e.message == "All categories must be provided."
    }
}
