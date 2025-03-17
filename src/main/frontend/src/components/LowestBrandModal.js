import {useEffect, useState} from "react";
import axios from "axios";

const LowestBrandModal = ({isOpen, onClose}) => {
  const endpoint = "/api/price-comparison/lowest-brand";

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    if (isOpen) {
      setLoading(true);
      setError(null);

      axios.get(endpoint)
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
  }, [isOpen]);

  if (!isOpen) return null;

  return (
    <div className="modal">
      <div className="modal-content">
        <h2>단일 브랜드 최저가 조회</h2>

        {loading && <p>데이터 로딩 중...</p>}
        {error && <p style={{color: "red"}}>{error}</p>}

        {data && data.brand && (
          <p>
            브랜드: {data.brand.name}
          </p>
        )}
        {data && (
          <table>
            <thead>
            <tr>
              <th>카테고리</th>
              <th>가격</th>
            </tr>
            </thead>
            <tbody>
            {data.products.map((p, index) => (
              <tr key={index}>
                <td>{p.category.koreanName}</td>
                <td>{p.price}</td>
              </tr>
            ))}
            </tbody>
            <tfoot>
            <tr className="total-row">
              <td>총액</td>
              <td>{data.totalPrice}</td>
            </tr>
            </tfoot>
          </table>
        )}

        <button onClick={onClose}>닫기</button>
      </div>
    </div>
  );
};

export default LowestBrandModal;