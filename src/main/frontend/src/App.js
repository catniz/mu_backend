import './App.css';
import {useState} from "react";
import MinMaxModal from "./components/MinMaxModal";
import LowestByAllCategoryModal from "./components/LowestByAllCategoryModal";
import LowestBrandModal from "./components/LowestBrandModal";

import ManageBrandProductModal from "./components/ManageBrandProductModal";
import ManageBrandModal from "./components/ManageBrandModal";

function App() {
  const [lowestByCategoryModalOpen, setLowestByCategoryModalOpen] = useState(false);
  const [lowestBrandModalOpen, setLowestBrandModalOpen] = useState(false);
  const [minMaxModalOpen, setMinMaxModalOpen] = useState(false);
  const [manageBrandModalOpen, setManageBrandModalOpen] = useState(false);
  const [manageBrandProductModalOpen, setManageBrandProductModalOpen] = useState(false);

  return (
    <div className="App">
      <header className="App-header">
        <div>
          <div>
            <button onClick={() => setLowestByCategoryModalOpen(true)}>모든 카테고리별 최저가 조회</button>
          </div>
          <div>
            <button onClick={() => setLowestBrandModalOpen(true)}>단일 브랜드 최저가 조회</button>
          </div>
          <div>
            <button onClick={() => setMinMaxModalOpen(true)}>카테고리별 최저/최고가 조회</button>
          </div>
          <div>
            <button onClick={() => setManageBrandModalOpen(true)}>브랜드 관리</button>
          </div>
          <div>
            <button onClick={() => setManageBrandProductModalOpen(true)}>브랜드 상품 관리</button>
          </div>
        </div>
      </header>

      <LowestByAllCategoryModal isOpen={lowestByCategoryModalOpen} onClose={() => setLowestByCategoryModalOpen(false)}/>
      <LowestBrandModal isOpen={lowestBrandModalOpen} onClose={() => setLowestBrandModalOpen(false)}/>
      <MinMaxModal isOpen={minMaxModalOpen} onClose={() => setMinMaxModalOpen(false)}/>
      <ManageBrandModal isOpen={manageBrandModalOpen} onClose={() => setManageBrandModalOpen(false)}/>
      <ManageBrandProductModal isOpen={manageBrandProductModalOpen} onClose={() => setManageBrandProductModalOpen(false)}/>

    </div>
  );
}

export default App;
