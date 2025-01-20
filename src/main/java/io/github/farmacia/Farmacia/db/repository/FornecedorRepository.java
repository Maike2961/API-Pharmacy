package io.github.farmacia.Farmacia.db.repository;

import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.model.FornecedorLocais;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FornecedorRepository extends JpaRepository<Fornecedor, UUID> {
    List<Fornecedor> findByCompanhia(String companhia);
    Optional<Fornecedor> findByCompanhiaAndLocal(String companhia, FornecedorLocais local);
}
