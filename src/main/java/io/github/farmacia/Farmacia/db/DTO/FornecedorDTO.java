package io.github.farmacia.Farmacia.db.DTO;

import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.model.FornecedorLocais;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FornecedorDTO(
        @NotBlank(message = "Companhia é obrigatório")
        String companhia,
        @NotNull(message = "Local é obrigatório")
        FornecedorLocais local) {

    public Fornecedor mapear(){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setCompanhia(this.companhia);
        fornecedor.setLocal(this.local);
        return fornecedor;
    }
}
