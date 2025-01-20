package io.github.farmacia.Farmacia.db.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "fornecedor")
@Getter
@Setter
@ToString(exclude = "item")
@EntityListeners(AuditingEntityListener.class)
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "companhia", nullable = false)
    private String companhia;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "local", nullable = false)
    private FornecedorLocais local;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
    private List<Item> item;
}
