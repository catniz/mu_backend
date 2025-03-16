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

    def "getLowestByCategory - normal"() {
        given:
        def targetCategories = [Category.TOP, Category.OUTER]
        def ABrandTopProduct = createProductDto(1, "A", Category.TOP, 11200)
        def ABrandOuterProduct = createProductDto(2, "A", Category.OUTER, 5500)
        def BBrandTopProduct = createProductDto(3, "B", Category.TOP, 10500)
        def BBrandOuterProduct = createProductDto(4, "B", Category.OUTER, 5900)
        def CBrandTopProduct = createProductDto(5, "C", Category.TOP, 10000)
        def CBrandOuterProduct = createProductDto(6, "C", Category.OUTER, 6200)
        productService.getByCategory(Category.TOP) >> [ABrandTopProduct, BBrandTopProduct, CBrandTopProduct]
        productService.getByCategory(Category.OUTER) >> [ABrandOuterProduct, BBrandOuterProduct, CBrandOuterProduct]

        when:
        def result = service.getLowestByCategory(targetCategories)

        then:
        result.totalPrice == 15500
        result.products.size() == 2
        result.products.get(0).properties == CBrandTopProduct.properties
        result.products.get(1).properties == ABrandOuterProduct.properties
    }

    def "getLowestByCategory - 카테고리가 하나라도 빈 경우"() {
        given:
        def targetCategories = [Category.TOP, Category.OUTER]
        def ABrandTopProduct = createProductDto(1, "A", Category.TOP, 11200)
        def BBrandTopProduct = createProductDto(3, "B", Category.TOP, 10500)
        def CBrandTopProduct = createProductDto(5, "C", Category.TOP, 10000)
        productService.getByCategory(Category.TOP) >> [ABrandTopProduct, BBrandTopProduct, CBrandTopProduct]

        when:
        service.getLowestByCategory(targetCategories)

        then:
        def e = thrown(EntityNotFoundException)
        e.message == "No products found for the given category OUTER"
    }

    def "GetCategoryMinMaxPrice - normal"() {
        given:
        def category = Category.TOP

        def ABrandTopProduct = createProductDto(1, "A", category, 11200)
        def BBrandTopProduct = createProductDto(2, "B", category, 10500)
        def CBrandTopProduct = createProductDto(3, "C", category, 10000)
        productService.getByCategory(category) >> [ABrandTopProduct, BBrandTopProduct, CBrandTopProduct]

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
        e.message.contains("No products found for the given category")
    }

    private static ProductResponseDto createProductDto(long id, String brandName, Category category, int price) {
        return new ProductResponseDto(id, new BrandResponseDto(id, brandName), category, price)
    }
}
