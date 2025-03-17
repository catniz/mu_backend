# mu_backend
기술 스택: Java, Spring Boot, Gradle, JPA, H2 Database

## 프로젝트 실행 방법
```shell
# clean & build
./gradlew clean build --refresh-dependencies

# test
./gradlew clean test

# start application - 실행 포트: 8080
./gradlew bootRun

```
- 초기 데이터는 [import.sql](src/main/resources/import.sql)을 참고해주세요.
## 구현 기능
1. 모든 카테고리의 최저가격 상품, 가격 총합 조회  
   - 카테고리별 최저가격인 브랜드와 가격을 조회하고 총액을 확인할 수 있습니다.
2. 최저 가격의 단일 브랜드 조회
   - 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액을 확인할 수 있습니다.
3. 특정 카테고리의 최저/최고 가격 브랜드 상픔 조회
   - 특정 카테고리에서 최저/최고 가격 브랜드와 각 상품의 가격을 확인할 수 있습니다.
4. 브랜드 및 상품 관리
   - 새로운 브랜드를 등록하고, 모든 브랜드의 상품을 추가, 변경, 삭제할 수 있습니다.

#### 제약 사항
- 각 브랜드는 각 카테고리에 대해 최소 하나 이상의 상품을 가지고 있습니다.
- 구매 가격 외의 비용은 고려하지 않습니다.

## API 명세
### 공통
- HTTP Status Code
   - 정상 응답인 경우 200 OK
   - 200이 아닌 경우, 응답의 메세지를 확인해주세요.
      ``` 
      error: string
      message: string
      ```
- Content Type
   - Content-Type: application/json
   - Request, Response body는 항상 json 포맷을 사용합니다.
- Category
  - model.Category 파일 참고
    ``` java
    public enum Category {
        TOP("상의"),
        OUTER("아우터"),
        PANTS("바지"),
        SNEAKERS("스니커즈"),
        BAG("가방"),
        HAT("모자"),
        SOCKS("양말"),
        ACCESSORY("액세서리");
    }
    ```


### 구현 1. 모든 카테고리의 최저가격 상품, 가격 총합 조회
#### GET /api/price-comparison/lowest-by-all-category
- 카테고리별 최저가격인 브랜드와 가격을 조회하고 총액을 확인할 수 있습니다.
- Response
  - [LowestPriceProductsByCategoryDto.java](src/main/java/com/musinsa/backend/dto/LowestPriceProductsByCategoryDto.java)
    ```
      totalPrice: long
      products: []object
        id: long
        brand: object
          id: long
          name: string
        category: object
          name: string
          koreanName: string
        price: long
    ```

### 구현 2. 최저 가격의 단일 브랜드 조회
#### GET /api/price-comparison/lowest-brand
- 단일 브랜드로 전체 카테고리 상품을 구매할 경우 최저가격인 브랜드와 총액을 확인할 수 있습니다.
- Response
    - [LowestPriceBrandDto.java](src/main/java/com/musinsa/backend/dto/LowestPriceBrandDto.java)
      ```
        brand: object
          id: long
          name: string
        totalPrice: long
        products: []object
          id: long
          brand: object
            id: long
            name: string
          category: object
            name: string
            koreanName: string
          price: long
      ```

### 구현 3. 특정 카테고리의 최저/최고 가격 브랜드 상픔 조회
#### GET /api/price-comparison/min-max?category={category}
- 특정 카테고리에서 최저/최고 가격 브랜드와 각 상품의 가격을 확인할 수 있습니다.
- Request Param
  - category: 조회하고자 하는 category
- Response
    - [CategoryMinMaxPriceDto.java](src/main/java/com/musinsa/backend/dto/CategoryMinMaxPriceDto.java)
      ```
        category: object
          name: string
          koreanName: string
        minProduct: object
          id: long
          brand: object
            id: long
            name: string
          category: object
            name: string
            koreanName: string
          price: long
        maxProduct: object
          id: long
          brand: object
            id: long
            name: string
          category: object
            name: string
            koreanName: string
          price: long
      ```

### 구현 4. 브랜드 및 상품 관리
#### POST /api/brand
- 새로운 브랜드 등록
  - 새로운 브랜드는 항상 모든 카테고리의 상품을 1개씩 등록해야 합니다.
- Request Body
  - [BrandCreateDto.java](src/main/java/com/musinsa/backend/dto/BrandCreateDto.java)
    ```
      name: string
      products: []object
        category: string
        price: long
    
      # example
      {
        "name": "new",
        "products": [
            {"category": "top", "price": 10000},
            {"category": "OUTER", "price": 2000},
            {"category": "PANTS", "price": 3000},
            {"category": "SNEAKERS", "price": 4000},
            {"category": "BAG", "price": 5000},
            {"category": "HAT", "price": 6000},
            {"category": "SOCKS", "price": 7000},
            {"category": "ACCESSORY", "price": 8000}
        ]
      }
    ```
- Response
  - 200 OK. No contents.

#### PUT /api/brand/{brandId}
- 브랜드 이름 변경
- Request
  - Path
    - brandId: 변경하고자 하는 brand Id
  - Body
    - [BrandUpdateDto.java](src/main/java/com/musinsa/backend/dto/BrandUpdateDto.java)
      ```
        name: string
        
        # example
        {
          "name": "update"
        }
      ```
- Response
    - 200 OK. No contents.

#### DELETE /api/brand/{brandId}
- 브랜드 삭제. 해당 브랜드의 모든 상품도 삭제합니다.
- Request Path
  - brandId: 삭제하고자 하는 brand Id
- Response
    - 200 OK. No contents.

#### POST /api/brand/{brandId}/product
- 특정 브랜드에 새로운 상품 등록
- Request
  - Path
    - brandId: 추가하려는 brand Id
  - Body
    - [ProductCreateDto.java](src/main/java/com/musinsa/backend/dto/ProductCreateDto.java)
      ```
        category: string
        price: long
        
        # example
        {
          "category": "top"
          "price": 13000
        }
      ```
- Response
    - 200 OK. No contents.

#### PUT /api/brand/product/{productId}
- 상품 가격 변경
- Request
  - Path
    - productId: 변경하고자 하는 product Id
  - Body
    - [ProductUpdateDto.java](src/main/java/com/musinsa/backend/dto/ProductUpdateDto.java)
      ```
        price: long
          
        # example
        {
          "price": "13000"
        }
      ```
- Response
    - 200 OK. No contents.

#### DELETE /api/brand/product/{productId}
- 상품 삭제. 해당 상품이 속한 브랜드에 동일한 카테고리 상품이 없다면 삭제할 수 없습니다.
- Request Path
  - productId: 삭제하고자 하는 product Id
- Response
  - 200 OK. No contents.

