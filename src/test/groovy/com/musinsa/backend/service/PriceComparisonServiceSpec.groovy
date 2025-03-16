package com.musinsa.backend.service

import com.musinsa.backend.dto.BrandResponseDto
import com.musinsa.backend.dto.ProductResponseDto
import com.musinsa.backend.model.Category
import jakarta.persistence.EntityNotFoundException
import spock.lang.Specification
import spock.lang.Subject

class PriceComparisonServiceSpec extends Specification {
    def productService = Mock(ProductService)

    @Subject
    def service = new PriceComparisonService(productService)

    def "GetCategoryMinMaxPrice - normal"() {
        given:
        def category = Category.TOP

        def ABrandTopProduct = createProductDto(1, "A", category, 11200)
        def BBrandTopProduct = createProductDto(2, "B", category, 10500)
        def CBrandTopProduct = createProductDto(3, "C", category, 10000)
        productService.getByCategory(category) >> [ABrandTopProduct,BBrandTopProduct,CBrandTopProduct]

        when:
        def result = service.getCategoryMinMaxPrice(category)

        then:
        result.category == category
        result.minPriceProduct.properties == CBrandTopProduct.properties
        result.maxPriceProduct.properties == ABrandTopProduct.properties
    }

    def "GetCategoryMinMaxPrice - 하나만 있을 경우, min==max"() {
        given:
        def category = Category.TOP

        def ABrandTopProduct = createProductDto(1, "A", category, 11200)
        productService.getByCategory(category) >> [ABrandTopProduct]

        when:
        def result = service.getCategoryMinMaxPrice(category)

        then:
        result.category == category
        result.minPriceProduct.properties == ABrandTopProduct.properties
        result.maxPriceProduct.properties == ABrandTopProduct.properties
    }

    def "GetCategoryMinMaxPrice - 하나도 없을 경우"() {
        given:
        def category = Category.TOP

        productService.getByCategory(category) >> []

        when:
        service.getCategoryMinMaxPrice(category)

        then:
        def e = thrown(EntityNotFoundException)
        e.message == "No products found for the given category."
    }

    private static ProductResponseDto createProductDto(long id, String brandName, Category category, int price) {
        return new ProductResponseDto(id, new BrandResponseDto(id, brandName), category, price)

    }
}
