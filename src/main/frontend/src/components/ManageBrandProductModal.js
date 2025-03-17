import {useEffect, useState} from "react";
import axios from "axios";

const ManageBrandProductModal = ({isOpen, onClose}) => {
  const [selectedBrand, setSelectedBrand] = useState("");
  const [categories, setCategories] = useState([]);
  const [brands, setBrands] = useState([]);
  const [products, setProducts] = useState([]);

  // add product
  const [selectedCategory, setSelectedCategory] = useState("");
  const [newProductPrice, setNewProductPrice] = useState("");

  // edit product
  const [editPriceId, setEditPriceId] = useState(null);
  const [newPrice, setNewPrice] = useState("")

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (isOpen) {
      axios.get("/api/brand/all")
        .then(response => {
          setBrands(response.data)
          if(response.data.length > 0) {
            setSelectedBrand(response.data[0].id)
          }
        })
        .catch(error => console.error("브랜드 조회 실패:", error));

      axios.get("/api/brand/category/all")
        .then(response => setCategories(response.data))
        .catch(error => console.error("카테고리 조회 실패:", error));
    }
  }, [isOpen]);

  useEffect(() => {
    if (selectedBrand) {
      setLoading(true);
      axios.get(`/api/brand/${selectedBrand}`)
        .then(response => {
          setProducts(response.data.products);
          setLoading(false);
        })
        .catch(error => {
          console.error("제품 조회 실패:", error);
          setLoading(false);
          setError("데이터를 불러오는 데 실패했습니다.");
        });
      setLoading(false);
    }
  }, [selectedBrand]);

  const handleAddProduct = () => {
    if (!selectedBrand || !selectedCategory || !newProductPrice) {
      alert("브랜드, 카테고리, 가격을 입력하세요.");
      return;
    }

    const brandId = Number(selectedBrand)
    const newProduct = {
      category: selectedCategory,
      price: Number(newProductPrice),
    };

    axios.post(`/api/brand/${brandId}/product`, newProduct)
      .then(response => {
        setProducts([...products, response.data]); // 새 상품 추가
        setNewProductPrice(""); // 입력 필드 초기화
      })
      .catch(error => {
        console.error("상품 추가 실패:", error);
        alert("상품 추가에 실패했습니다. \n" + error.response?.data?.message);
      });
  };

  const handleDelete = (productId) => {
    axios.delete(`/api/brand/product/${productId}`)
      .then(() => {
        setProducts(products.filter(product => product.id !== productId));
      })
      .catch(error => {
        console.error("삭제 실패:", error)
        alert("삭제에 실패했습니다. \n" + error.response?.data?.message);
      });
  };

  // 수정 버튼 클릭 시 가격 입력 필드 활성화
  const handleEdit = (productId, currentPrice) => {
    setEditPriceId(productId);
    setNewPrice(currentPrice);
  };

  const handleUpdatePrice = (productId) => {
    axios.put(`/api/brand/product/${productId}`, {price: newPrice})
      .then(() => {
        setProducts(products.map(product =>
          product.id === productId ? {...product, price: newPrice} : product
        ));
        setEditPriceId(null); // 수정 완료 후 초기화
      })
      .catch(error => {
        console.error("가격 수정 실패:", error)
        alert("수정에 실패했습니다. \n" + error.response?.data?.message);
      });
  };

  if (!isOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>브랜드 상품 관리</h2>

        <label>브랜드 선택: </label>
        <select value={selectedBrand} onChange={(e) => setSelectedBrand(e.target.value)}>
          <option value="">브랜드를 선택하세요</option>
          {brands.map((brand) => (
            <option key={brand.id} value={brand.id}>{brand.name}</option>
          ))}
        </select>

        {loading && <p>데이터 로딩 중...</p>}
        {error && <p style={{color: "red"}}>{error}</p>}

        <div className="add-form">
          <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
            <option value="">카테고리를 선택하세요</option>
            {categories.map((category, index) => (
              <option key={index} value={category.name}>{category.koreanName}</option>
            ))}
          </select>
          <input
            type="number"
            value={newProductPrice}
            onChange={(e) => setNewProductPrice(e.target.value)}
            placeholder="가격"
          />
          <button style={{width: "120px"}} onClick={handleAddProduct}>상품 추가</button>
        </div>


        {products.length > 0 ? (
          <table>
            <thead>
            <tr>
              <th>카테고리</th>
              <th>가격</th>
              <th style={{width: "100px"}}>수정</th>
              <th style={{width: "100px"}}>삭제</th>
            </tr>
            </thead>
            <tbody>
            {products.map((product) => (
              <tr key={product.id}>
                <td>{product.category.koreanName}</td>

                {/* ✅ 가격 수정 필드 */}
                <td>
                  {editPriceId === product.id ? (
                    <input
                      type="number"
                      value={newPrice}
                      onChange={(e) => setNewPrice(e.target.value)}
                    />
                  ) : (
                    `${product.price}`
                  )}
                </td>

                {/* ✅ 수정 버튼 */}
                <td>
                  {editPriceId === product.id ? (
                    <button className="small-btn" onClick={() => handleUpdatePrice(product.id)}>저장</button>
                  ) : (
                    <button className="small-btn" onClick={() => handleEdit(product.id, product.price)}>수정</button>
                  )}
                </td>

                {/* ✅ 삭제 버튼 */}
                <td>
                  <button className="small-btn" onClick={() => handleDelete(product.id)}>삭제</button>
                </td>
              </tr>
            ))}
            </tbody>
          </table>
        ) : (
          selectedBrand && <p>등록된 제품이 없습니다.</p>
        )}

        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};
export default ManageBrandProductModal;