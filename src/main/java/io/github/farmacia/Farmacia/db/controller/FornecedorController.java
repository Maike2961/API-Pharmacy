package io.github.farmacia.Farmacia.db.controller;

import io.github.farmacia.Farmacia.db.DTO.FornecedorDTO;
import io.github.farmacia.Farmacia.db.DTO.PesquisaFornecedorDTO;
import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.service.FornecedorService;
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
@RequestMapping("fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
            Fornecedor mapear = fornecedorDTO.mapear();
            service.salvar(mapear);
            URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(mapear.getId())
                    .toUri();
            return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterPorID(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        Optional<Fornecedor> fornecedor = service.getById(uuid);

        if (fornecedor.isPresent()) {
            Fornecedor fornecedor1 = fornecedor.get();
            FornecedorDTO fornecedorDTO = new FornecedorDTO(
                    fornecedor1.getCompanhia(),
                    fornecedor1.getLocal());
            return ResponseEntity.ok(fornecedorDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<PesquisaFornecedorDTO>> pesquisa(
            @RequestParam(value = "companhia", required = false) String companhia) {
        List<Fornecedor> pesquisa = service.pesquisa(companhia);
        List<PesquisaFornecedorDTO> fornecedorDTOStream = pesquisa.stream().map(
                fornecedor -> new PesquisaFornecedorDTO(
                        fornecedor.getId(),
                        fornecedor.getCompanhia(),
                        fornecedor.getLocal()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(fornecedorDTOStream);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(
            @Valid @PathVariable("id") String id,
            @RequestBody FornecedorDTO fornecedorDTO) {
            UUID uuid = UUID.fromString(id);
            Optional<Fornecedor> fornecedor = service.getById(uuid);
            if (fornecedor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Fornecedor fornecedor1 = fornecedor.get();
            fornecedor1.setCompanhia(fornecedorDTO.companhia());
            fornecedor1.setLocal(fornecedorDTO.local());
            service.atualizar(fornecedor1);
            return ResponseEntity.ok(fornecedorDTO);
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
            UUID uuid = UUID.fromString(id);

            Optional<Fornecedor> fornecedor = service.getById(uuid);

            if (fornecedor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.deletar(fornecedor.get());
            return ResponseEntity.noContent().build();
    }

}
