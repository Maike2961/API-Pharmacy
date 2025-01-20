package io.github.farmacia.Farmacia.db.DTO;

import java.util.UUID;

import io.github.farmacia.Farmacia.db.model.FornecedorLocais;

public record PesquisaFornecedorDTO(UUID id, String companhia, FornecedorLocais local) {
    
}
