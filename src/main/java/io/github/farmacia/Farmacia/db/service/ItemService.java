package io.github.farmacia.Farmacia.db.service;

import io.github.farmacia.Farmacia.db.model.Item;
import io.github.farmacia.Farmacia.db.repository.ItemRepository;
import io.github.farmacia.Farmacia.db.validador.ValidadorItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    @Autowired
    private ValidadorItem validadorItem;

    public Item salvar(Item item){
        validadorItem.validar(item);
        System.out.println(item);
        return repository.save(item);
    }

    public Optional<Item> obterPorID(UUID id){
        return repository.findById(id);
    }

    public List<Item> pesquisar(String nome){
        if(nome != null){
            List<Item> byNome = repository.findByNomeLike("%" + nome + "%");
            return byNome;
        }
        return repository.findAll();
    }

    public Item atualizar(Item item){
        if(item.getId() == null){
            throw new IllegalArgumentException("Para Atualizar, é necessario ter um item cadastrado");
        }
        validadorItem.validar(item);
        return repository.save(item);
    }

    public void deletar(Item item){
        repository.delete(item);
    }

}
