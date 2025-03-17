package com.musinsa.backend.service

import com.musinsa.backend.model.Brand
import com.musinsa.backend.model.Category
import com.musinsa.backend.model.Product
import com.musinsa.backend.repository.BrandRepository
import com.musinsa.backend.repository.ProductRepository
import spock.lang.Specification
import spock.lang.Subject


class BrandProductServiceSpec extends Specification {
    def brandRepository = Mock(BrandRepository)
    def productRepository = Mock(ProductRepository)

    @Subject
    def service = new BrandProductService(brandRepository, productRepository)

    def "deleteProduct - normal"() {
        given:
        def brandId = 1L
        def productId = 1L
        def category = Category.TOP
        def deletedProduct = createProductEntity(productId, brandId, category, 1000)
        productRepository.findById(productId) >> Optional.of(deletedProduct)
        productRepository.findByBrandIdAndCategory(brandId, category) >> [
                deletedProduct, createProductEntity(2L, brandId, category, 2000)
        ]

        when:
        service.deleteProduct(productId)

        then:
        1 * productRepository.delete({ Product p ->
            p.id == productId &&
                    p.brand.id == brandId &&
                    p.category == category &&
                    p.price == 1000
        })
    }

    def "deleteProduct - 지운 후에도 카테고리에 최소 하나의 상품은 있어야 함"() {
        given:
        def brandId = 1L
        def productId = 1L
        def category = Category.TOP
        def deletedProduct = createProductEntity(productId, brandId, category, 1000)
        productRepository.findById(productId) >> Optional.of(deletedProduct)
        productRepository.findByBrandIdAndCategory(brandId, category) >> [deletedProduct]

        when:
        service.deleteProduct(productId)

        then:
        0 * productRepository.delete(_)
        def e = thrown(IllegalStateException)
        e.message.contains("At least one product must remain in brand")
    }

    private static createProductEntity(Long productId, Long brandId, Category category, Long price) {
        return new Product(id: productId, brand: new Brand(id: brandId, name: "A"), category: category, price: price)
    }

}
