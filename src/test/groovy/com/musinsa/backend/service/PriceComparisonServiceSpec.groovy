package com.musinsa.backend.service

import com.musinsa.backend.dto.BrandResponseDto
import com.musinsa.backend.dto.BrandWithProductsDto
import com.musinsa.backend.dto.ProductResponseDto
import com.musinsa.backend.model.Category
import jakarta.persistence.EntityNotFoundException
import spock.lang.Specification
import spock.lang.Subject

class PriceComparisonServiceSpec extends Specification {
    def productService = Mock(ProductService)
    def brandService = Mock(BrandService)

    @Subject
    def service = new PriceComparisonService(brandService, productService)

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

    def "getLowestBrand - normal"() {
        given:
        brandService.getAllBrandsWithProducts() >> [
                createBrandWithProducts("A", [
                        (Category.TOP): [11200],
                        (Category.OUTER): [5500],
                        (Category.PANTS): [4200],
                        (Category.SNEAKERS): [9000],
                        (Category.BAG): [2000],
                        (Category.HAT): [1700],
                        (Category.SOCKS): [1800],
                        (Category.ACCESSORY): [2300],
                ]),
                createBrandWithProducts("B", [
                        (Category.TOP): [10500],
                        (Category.OUTER): [5900],
                        (Category.PANTS): [3800],
                        (Category.SNEAKERS): [9100],
                        (Category.BAG): [2100],
                        (Category.HAT): [2000],
                        (Category.SOCKS): [2000],
                        (Category.ACCESSORY): [2200],
                ]),
                createBrandWithProducts("D", [
                        (Category.TOP): [10300, 10100, 17000],
                        (Category.OUTER): [5100],
                        (Category.PANTS): [3000],
                        (Category.SNEAKERS): [9500],
                        (Category.BAG): [2500],
                        (Category.HAT): [1500],
                        (Category.SOCKS): [2400],
                        (Category.ACCESSORY): [2000],
                ])
        ]

        when:
        def result = service.getLowestBrand()

        then:
        result.totalPrice == 36100
        result.brand.name == "D"
        result.products.size() == Category.validValues().size()
    }

    def "getLowestBrand - 조건 만족하는 경우가 하나라도 있으면 결과 반환"() {
        given:
        brandService.getAllBrandsWithProducts() >> [
                createBrandWithProducts("A", [
                        (Category.TOP): [11200],
                        (Category.OUTER): [5500],
                        (Category.PANTS): [4200],
                ]),
                createBrandWithProducts("D", [
                        (Category.TOP): [10300, 10100, 17000],
                        (Category.OUTER): [5100],
                        (Category.PANTS): [3000],
                        (Category.SNEAKERS): [9500],
                        (Category.BAG): [2500],
                        (Category.HAT): [1500],
                        (Category.SOCKS): [2400],
                        (Category.ACCESSORY): [2000],
                ])
        ]

        when:
        def result = service.getLowestBrand()

        then:
        result.totalPrice == 36100
        result.brand.name == "D"
        result.products.size() == Category.validValues().size()
    }

    def "getLowestBrand - 조건 만족하는 경우가 없으면 에러"() {
        given:
        brandService.getAllBrandsWithProducts() >> [
                createBrandWithProducts("A", [
                        (Category.TOP): [11200],
                        (Category.OUTER): [5500],
                        (Category.PANTS): [4200],
                ]),
                createBrandWithProducts("D", [
                        (Category.TOP): [10300, 10100, 17000],
                        (Category.OUTER): [5100],
                ])
        ]

        when:
        service.getLowestBrand()

        then:
        def e = thrown(NoSuchElementException)
        e.message.contains("No brand has products in all required categories")
    }

    def "getCategoryMinMaxPrice - normal"() {
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

    def "getCategoryMinMaxPrice - 하나만 있을 경우, min==max"() {
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

    def "getCategoryMinMaxPrice - 하나도 없을 경우"() {
        given:
        def category = Category.TOP

        productService.getByCategory(category) >> []

        when:
        service.getCategoryMinMaxPrice(category)

        then:
        def e = thrown(EntityNotFoundException)
        e.message.contains("No products found for the given category")
    }

    private static BrandWithProductsDto createBrandWithProducts(String brandName, Map<Category, List<Long>> products) {
        BrandResponseDto brand = new BrandResponseDto(0, brandName)
        List<ProductResponseDto> p = products.entrySet().stream().flatMap{ entry ->
            entry.getValue().stream().map {price -> new ProductResponseDto(0, brand, entry.getKey(), price)}
        }.toList()

        return new BrandWithProductsDto(brand.getId(), brandName, p)
    }

    private static ProductResponseDto createProductDto(long id, String brandName, Category category, int price) {
        return new ProductResponseDto(id, new BrandResponseDto(0, brandName), category, price)
    }
}
