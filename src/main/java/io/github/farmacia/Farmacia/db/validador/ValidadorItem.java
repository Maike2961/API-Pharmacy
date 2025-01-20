package io.github.farmacia.Farmacia.db.validador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.farmacia.Farmacia.db.exceptions.ResgistroDuplicado;
import io.github.farmacia.Farmacia.db.model.Item;
import io.github.farmacia.Farmacia.db.repository.ItemRepository;

@Component
public class ValidadorItem {

    @Autowired
    private ItemRepository itemRepository;

    public void validar(Item item){
        if(existsItem(item)){
            throw new ResgistroDuplicado("Item já cadastrado");
        }
    }

    public boolean existsItem(Item item){
        Optional<Item> byId = itemRepository.findByNomeAndQuantidade(item.getNome(), item.getQuantidade());
        
        if(item.getId() == null){
            return byId.isPresent();
        }
        return byId.filter(existingItem -> !item.getId().equals(existingItem.getId())).isPresent();
    }
    
}
