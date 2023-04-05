import React, {useEffect, useState} from "react";
import axios from "axios";

function App() {

  const [empresa, setEmpresa] = {nomeFantasia: '', cnpj: '', bairro: '' };

  


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

    <div class="container">
      <br />
      <table class="table">
        <thead>
          <tr>
            <th scope="col">Nome Fantasia</th>
            <th scope="col">CNPJ</th>
            <th scope="col">Bairro</th>
          </tr>
        </thead>
      </table>
    </div>
    

    </div>
  );
}

export default App;