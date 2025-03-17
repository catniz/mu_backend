import axios from "axios";
import {useEffect, useState} from "react";

const MinMaxModal = ({isOpen, onClose}) => {
  const endpoint = "/api/price-comparison/min-max";

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");

  useEffect(() => {
    if (isOpen) {
      axios.get("/api/brand/category/all")
        .then(response => {
          setCategories(response.data);
          if (response.data.length > 0) {
            setSelectedCategory(response.data[0].name);
          }
        })
        .catch(error => console.error("카테고리 불러오기 실패:", error));
    }
  }, [isOpen]);

  useEffect(() => {
    if (isOpen && selectedCategory) {
      setLoading(true);
      setError(null);

      axios.get(`${endpoint}?category=${selectedCategory}`)
        .then(response => {
          setData(response.data);
          setLoading(false);
        })
        .catch(error => {
          console.error("데이터 불러오기 실패:", error);
          setError("데이터를 불러오는 데 실패했습니다. \n" + error.response?.data?.message);
          setLoading(false);
        });
    }
  }, [isOpen, selectedCategory]);

  if (!isOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>카테고리별 최저/최고가</h2>

        <select value={selectedCategory} onChange={(e) => setSelectedCategory(e.target.value)}>
          {categories.map((category, index) => (
            <option key={index} value={category.name}>{category.name} ({category.koreanName})</option>
          ))}
        </select>

        {loading && <p>데이터 로딩 중...</p>}
        {error && <p style={{color: "red"}}>{error}</p>}

        {data && (
          <table>
            <thead>
            <tr>
              <th></th>
              <th>브랜드</th>
              <th>가격</th>
            </tr>
            </thead>
            <tbody>
            {data.minPriceProduct && (
              <tr>
                <td>최저가</td>
                <td>{data.minPriceProduct.brand.name}</td>
                <td>{data.minPriceProduct.price}</td>
              </tr>
            )}
            {data.maxPriceProduct && (
              <tr>
                <td>최고가</td>
                <td>{data.maxPriceProduct.brand.name}</td>
                <td>{data.maxPriceProduct.price}</td>
              </tr>
            )}
            </tbody>
          </table>
        )}

        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};

export default MinMaxModal;