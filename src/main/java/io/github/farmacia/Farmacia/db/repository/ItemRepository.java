package io.github.farmacia.Farmacia.db.repository;

import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {

    boolean existsByFornecedor(Fornecedor fornecedor);

    List<Item> findByNome(String nome);

    Optional<Item> findByNomeAndQuantidade(String nome, int quantidade);


}
