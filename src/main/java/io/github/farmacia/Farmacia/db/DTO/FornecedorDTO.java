package io.github.farmacia.Farmacia.db.DTO;

import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.model.FornecedorLocais;

public record FornecedorDTO(String companhia, FornecedorLocais local) {

    public Fornecedor mapear(){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCompanhia(this.companhia);
        fornecedor.setLocal(this.local);
        return fornecedor;
    }
}
