package io.github.farmacia.Farmacia.db.validador;

import io.github.farmacia.Farmacia.db.exceptions.ResgistroDuplicado;
import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.repository.FornecedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class ValidadorFornecedor {

    @Autowired
    private FornecedorRepository fornecedorRepository;

    public void validar(Fornecedor fornecedor){
        if(existsFornecedor(fornecedor)){
            throw new ResgistroDuplicado("Fornecedor ja cadastrado com esse local");
        }
    }

    public boolean existsFornecedor(Fornecedor fornecedor){
        Optional<Fornecedor> fornecedor1 = fornecedorRepository.findByCompanhiaAndLocal(
            fornecedor.getCompanhia(), fornecedor.getLocal());

        if(fornecedor.getId() == null){
            return fornecedor1.isPresent();
        }
        return fornecedor1.map(t -> !t.getId().equals(fornecedor.getId())).orElse(false);
    }
}
