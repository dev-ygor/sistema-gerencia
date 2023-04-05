import { BrowserRouter, Route, Routes } from "react-router-dom";
import logo from './logo.svg';
import CadastroEmpresa from './service/cadastroEmpresa';
import CadastroPessoaFisica from './service/cadastroPessoaFisica';
import CadastroPessoaJuridica from './service/cadastroPessoaJuridica';
import Home from './home/home';

import './App.css';

function App() {
  return (
    <div>
      <CadastroPessoaFisica></CadastroPessoaFisica>
    </div>
    
    
  );
}

export default App;
