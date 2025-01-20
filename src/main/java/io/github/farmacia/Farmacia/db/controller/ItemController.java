package io.github.farmacia.Farmacia.db.controller;

import io.github.farmacia.Farmacia.db.DTO.ErroRespostaDTO;
import io.github.farmacia.Farmacia.db.DTO.FornecedorDTO;
import io.github.farmacia.Farmacia.db.DTO.ItemDTO;
import io.github.farmacia.Farmacia.db.DTO.PesquisaItemDTO;
import io.github.farmacia.Farmacia.db.exceptions.ResgistroDuplicado;
import io.github.farmacia.Farmacia.db.model.Item;
import io.github.farmacia.Farmacia.db.repository.FornecedorRepository;
import io.github.farmacia.Farmacia.db.service.ItemService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService service;

    @Autowired
    private FornecedorRepository repository;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid ItemDTO itemDTO) {
        try {
            Item mapear = itemDTO.mapear();
            mapear.setFornecedor(repository.findById(itemDTO.idFornecedor())
                    .orElseThrow(() -> new ResgistroDuplicado("Fornecedor não encontrado")));

            service.salvar(mapear);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(mapear.getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } catch (ResgistroDuplicado e) {
            ErroRespostaDTO erro = ErroRespostaDTO.respostaConflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<PesquisaItemDTO>> pesquisa(
            @RequestParam(value = "nome", required = false) String nome) {

        List<Item> pesquisar = service.pesquisar(nome);
        List<PesquisaItemDTO> resultado = pesquisar.stream()
                .map(i -> new PesquisaItemDTO(
                        i.getId(),
                        i.getNome(),
                        i.getPrecoCompra(),
                        i.getPrecoVenda(),
                        i.getQuantidade(),
                        i.getDescricao(),
                        new FornecedorDTO(
                                i.getFornecedor().getCompanhia(),
                                i.getFornecedor().getLocal())))
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> obterPorId(@PathVariable("id") String id) {

        UUID uuid = UUID.fromString(id);

        Optional<Item> item = service.obterPorID(uuid);

        if (item.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        Item item1 = item.get();

        ItemDTO itemDTO = new ItemDTO(
                item1.getNome(),
                item1.getPrecoCompra(),
                item1.getPrecoVenda(),
                item1.getQuantidade(),
                item1.getDescricao(),
                item1.getFornecedor().getId());
        return ResponseEntity.ok(itemDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody ItemDTO itemDTO) {
        try {
            UUID fromString = UUID.fromString(id);
            Optional<Item> obterPorID = service.obterPorID(fromString);
            if (obterPorID.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            Item item = obterPorID.get();
            item.setNome(itemDTO.nome());
            item.setPrecoCompra(itemDTO.precoCompra());
            item.setPrecoVenda(itemDTO.precoVenda());
            item.setQuantidade(itemDTO.quantidade());
            item.setFornecedor(repository.findById(itemDTO.idFornecedor())
                    .orElseThrow(() -> new ResgistroDuplicado("Item não encontrado")));
            service.atualizar(item);
            return ResponseEntity.ok(itemDTO);
        } catch (ResgistroDuplicado e) {
            ErroRespostaDTO resposta400 = ErroRespostaDTO.respostaConflito(e.getMessage());
            return ResponseEntity.status(resposta400.status()).body(resposta400);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        UUID fromString = UUID.fromString(id);

        return service.obterPorID(fromString).map(
                t -> {
                    service.deletar(t);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

}
