package io.github.farmacia.Farmacia.db.DTO;

import io.github.farmacia.Farmacia.db.model.Item;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDTO(
        @NotBlank(message = "Campo obrigatório")
        String nome, 
        @NotNull(message = "Campo obrigatório")
        BigDecimal precoCompra,
        @NotNull(message = "Campo obrigatório")
        BigDecimal precoVenda, 
        @Min(value = 1, message = "A quantidade deve ser maior que 0")
        int quantidade, 
        @NotBlank(message = "Campo obrigatório")
        String descricao,
        @NotNull(message = "Campo obrigatório")
        UUID idFornecedor) 
        {
    public Item mapear(){
        Item item = new Item();
        item.setNome(this.nome);
        item.setPrecoCompra(this.precoCompra);
        item.setPrecoVenda(this.precoVenda);
        item.setDescricao(this.descricao);
        item.setQuantidade(this.quantidade);
        return item;
    }

}
