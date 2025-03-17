import React, {useEffect, useState} from "react";
import axios from "axios";
import "../App.css";

const ManageBrandModal = ({isOpen, onClose}) => {
  const [brands, setBrands] = useState([]);
  const [categories, setCategories] = useState([]);
  const [newBrandName, setNewBrandName] = useState("");
  const [categoryPrices, setCategoryPrices] = useState({});
  const [editingBrand, setEditingBrand] = useState(null);
  const [updatedBrandName, setUpdatedBrandName] = useState("");

  useEffect(() => {
    if (isOpen) {
      axios.get("/api/brand/all")
        .then(response => setBrands(response.data))
        .catch(error => console.error("브랜드 조회 실패:", error));

      axios.get("/api/brand/category/all")
        .then(response => {
          setCategories(response.data);
          const initialPrices = {};
          response.data.forEach(category => {
            initialPrices[category.name] = "";
          });
          setCategoryPrices(initialPrices);
        })
        .catch(error => console.error("카테고리 조회 실패:", error));
    }
  }, [isOpen]);

  const handleAddBrand = () => {
    if (!newBrandName || Object.values(categoryPrices).some(price => price === "")) {
      alert("브랜드명과 모든 카테고리의 가격을 입력하세요.");
      return;
    }

    const newBrand = {
      name: newBrandName,
      products: Object.entries(categoryPrices).map(([k, v]) => ({
        category: k,
        price: v
      }))
    };

    axios.post("/api/brand", newBrand)
      .then(response => {
        setBrands([...brands, response.data]);
        setNewBrandName("");
        setCategoryPrices(categories.reduce((acc, cat) => ({...acc, [cat.name]: ""}), {}));
      })
      .catch(error => {
        console.error("브랜드 추가 실패:", error);
        alert("브랜드 추가에 실패했습니다. \n" + error.response?.data?.message);
      });
  };

  const handleDeleteBrand = (brandId) => {
    axios.delete(`/api/brand/${brandId}`)
      .then(() => {
        setBrands(brands.filter(brand => brand.id !== brandId));
      })
      .catch(error => {
        console.error("삭제 실패:", error);
        alert("브랜드 삭제에 실패했습니다. \n" + error.response?.data?.message);
      });
  };

  const handleEditBrand = (brandId, name) => {
    setEditingBrand(brandId);
    setUpdatedBrandName(name);
  };

  const handleUpdateBrand = (brandId) => {
    axios.put(`/api/brand/${brandId}`, {name: updatedBrandName})
      .then(() => {
        setBrands(brands.map(brand =>
          brand.id === brandId ? {...brand, name: updatedBrandName} : brand
        ));
        setEditingBrand(null);
      })
      .catch(error => {
        console.error("브랜드 수정 실패:", error);
        alert("브랜드 수정에 실패했습니다. \n" + error.response?.data?.message);
      });
  };

  if (!isOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>브랜드 관리</h2>

        <div className="add-form">
          <h3>브랜드 추가</h3>
          <input
            type="text"
            value={newBrandName}
            onChange={(e) => setNewBrandName(e.target.value)}
            placeholder="브랜드명 입력"
          />
          <h4>카테고리별 가격 입력</h4>
          <div className="category-container">
            {categories.map((category) => (
              <div key={category.name} className="category-input">
                <label>{category.koreanName}</label>
                <input
                  type="number"
                  style={{width: "130px"}}
                  value={categoryPrices[category.name]}
                  onChange={(e) => setCategoryPrices({...categoryPrices, [category.name]: e.target.value})}
                />
              </div>
            ))}
          </div>
          <button onClick={handleAddBrand}>브랜드 추가</button>
        </div>

        <h3>브랜드 목록</h3>
        {brands.length > 0 ? (
          <table>
            <thead>
            <tr>
              <th>브랜드명</th>
              <th style={{width: "100px"}}>수정</th>
              <th style={{width: "100px"}}>삭제</th>
            </tr>
            </thead>
            <tbody>
            {brands.map((brand) => (
              <tr key={brand.id}>
                <td>
                  {editingBrand === brand.id ? (
                    <input
                      type="text"
                      value={updatedBrandName}
                      onChange={(e) => setUpdatedBrandName(e.target.value)}
                    />
                  ) : (
                    brand.name
                  )}
                </td>
                <td>
                  {editingBrand === brand.id ? (
                    <button className="small-btn" onClick={() => handleUpdateBrand(brand.id)}>저장</button>
                  ) : (
                    <button className="small-btn" onClick={() => handleEditBrand(brand.id, brand.name)}>수정</button>
                  )}
                </td>
                <td>
                  <button className="small-btn" onClick={() => handleDeleteBrand(brand.id)}>삭제</button>
                </td>
              </tr>
            ))}
            </tbody>
          </table>
        ) : (
          <p>등록된 브랜드가 없습니다.</p>
        )
        }

        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};

export default ManageBrandModal;
