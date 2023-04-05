import React, {useEffect, useState} from "react";
import axios from "axios";
import pessoaFisica from "./cadastroPessoaFisica";

function App() {

  const [empresa, setEmpresa] = useState({nomeFantasia: '', 
                                          cnpj: '', 
                                          cep: '', 
                                          bairro: '', 
                                          cidade: '', 
                                          logradouro: '', 
                                          complemento: '', 
                                          uf: ''});

  const handleChange = (event) => {
    setEmpresa({...empresa, [event.target.name]:event.target.value})
  }

  const handleOnSubmitCep = (event) => {
    event.preventDefault();
    axios.post("http://localhost:8080/api/empresa/validarCEP", empresa).then(result => {

      if(result.status === 404) {
        console.log("não encontrado")
      }
      else {
        setEmpresa({...empresa, bairro: result.data.bairro, cidade: result.data.cidade, logradouro: result.data.logradouro, uf: result.data.uf})
      }
      
    });
  }

  const handleOnSubmit = (event) => {
    event.preventDefault();
    axios.post("http://localhost:8080/api/empresa/cadastro", empresa).then(result => {
      console.log(result);
    });

  const cepInvalido = () => {
       
      return (
          <div class="alert alert-danger" role="alert">
           CEP Inválido.
        </div>
        )
        
  }

  }

  return (

    <div >
      <nav class="navbar navbar-expand-lg bg-body-tertiary" id="navbarGeral">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">Gerencia</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="#">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Empresa</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Fornecedor</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>

    <div class="container-xl">
    <form onSubmit={handleOnSubmit}>
        <h1>Cadastro de Empresa</h1>
        <div class="container-colunas">
          <div class="col-5">
            <label for="InputNomeFantasia" class="form-label">Nome Fantasia</label>
            <input onChange={handleChange} value={empresa.nomeFantasia} name="nomeFantasia" type="text" class="form-control" id="inputNomeFantasia"></input>
          </div>
          <div class="col-5">
            <label for="inputCNPJ">CNPJ</label>
            <input onChange={handleChange} value={empresa.CNPJ} name="cnpj" type="text" class="form-control" id="inputCNPJ"></input>
          </div>
          
        </div>

        <div>
  
            <form class="row g-1">
              <div class="col-2">
                <label for="inputCEP" class="form-label">CEP</label>
                <input onChange={handleChange} value={empresa.cep} name="cep" type="text" class="form-control" id="inputCEP"></input>
              </div>

              <div class="col-1">
                <button type="submit" onClick={handleOnSubmitCep}  class="btn btn-primary mt-4 " id="btCEP">CEP</button>
              </div>

              <div class="col-2">
                <label for="inputBairro" class="form-label">Bairro</label>
                <input onChange={handleChange} value={empresa.bairro} name="bairro" type="text" class="form-control" id="inputBairro"></input>
              </div>

              <div class="row g-1">
                <div class="col-2">
                  <label for="inputCidade" class="form-label">Cidade</label>
                  <input onChange={handleChange} value={empresa.cidade} name="cidade" type="text" class="form-control" id="inputCidade"></input>
                 </div>
                 <div class="col-2">
                  <label for="inputLogradouro" class="form-label">Logradouro</label>
                  <input onChange={handleChange} value={empresa.logradouro} name="logradouro" type="text" class="form-control" id="inputLogradouro"></input>
                 </div>
                 <div class="col-1">
                  <label for="inputUf" class="form-label">UF</label>
                  <input onChange={handleChange} value={empresa.uf} name="uf" type="text" class="form-control" id="inputUf"></input>
                 </div>
              </div>
              <div class="col-5">
                <label for="inputComplemento">Complemento</label>
                <input onChange={handleChange} value={empresa.complemento} name="complemento" type="text" class="form-control" id="inputComplemento"></input>
              </div>
            </form>
        </div>

        <button type="submit" class="btn btn-outline-primary mt-4" id="btCadastrar">Cadastrar</button>

      </form>
    </div>
      

    </div>
  );
}

export default App;
