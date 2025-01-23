package io.github.farmacia.Farmacia.db.service;

import io.github.farmacia.Farmacia.db.exceptions.OperacaoNaoPermitida;
import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.repository.FornecedorRepository;
import io.github.farmacia.Farmacia.db.repository.ItemRepository;
import io.github.farmacia.Farmacia.db.validador.ValidadorFornecedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FornecedorService {

    @Autowired
    private FornecedorRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ValidadorFornecedor validadorFornecedor;

    public Fornecedor salvar(Fornecedor fornecedor){
        validadorFornecedor.validar(fornecedor);
        return repository.save(fornecedor);
    }

    public Optional<Fornecedor> getById(UUID id){
        return repository.findById(id);
    }

    public List<Fornecedor> pesquisa(String company){
        if(company != null){
            return repository.findByCompanhiaLike("%" + company + "%");
        }
        return repository.findAll();
    }

    public void atualizar(Fornecedor fornecedor){
        if(fornecedor.getId() == null){
            throw new IllegalArgumentException("Para Atualizar, é necessario ter um usuário cadastrado");
        }
        validadorFornecedor.validar(fornecedor);
        repository.save(fornecedor);
    }

    public void deletar(Fornecedor fornecedor){
        if(possuiItens(fornecedor)){
            throw new OperacaoNaoPermitida("Fornecedor tem itens cadastrados");
        }
        repository.delete(fornecedor);
    }

    public boolean possuiItens(Fornecedor fornecedor){
        return itemRepository.existsByFornecedor(fornecedor);
    }
}
