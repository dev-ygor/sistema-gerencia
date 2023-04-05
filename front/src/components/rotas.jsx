import { BrowserRouter, Route } from "react-router-dom";
import cadastroEmpresa from "../service/cadastroEmpresa";
import cadastroPessoaJuridica from "../service/cadastroPessoaJuridica";
import cadastroPessoaFisica from "../service/cadastroPessoaFisica";

function Rotas() {
  return (
    <BrowserRouter>
      <Route exact path="/cadastroPessoaJuridica" component={cadastroPessoaJuridica} />
      <Route path="/cadastroPessoaJuridica" component={cadastroPessoaJuridica} />
    </BrowserRouter>
  );
}

export default Rotas;