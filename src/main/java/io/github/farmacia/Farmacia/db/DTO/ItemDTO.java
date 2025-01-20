package io.github.farmacia.Farmacia.db.DTO;

import io.github.farmacia.Farmacia.db.model.Item;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemDTO(String nome, BigDecimal precoCompra,
                    BigDecimal precoVenda, int quantidade, String descricao ,UUID idFornecedor) {


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
