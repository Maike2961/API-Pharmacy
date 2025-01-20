package io.github.farmacia.Farmacia.db.DTO;

import java.math.BigDecimal;
import java.util.UUID;

public record PesquisaItemDTO(UUID id, String nome, BigDecimal precoCompra,
                        BigDecimal precoVenda, int quantidade, String descricao, FornecedorDTO fornecedorDTO) {
}