package io.github.farmacia.Farmacia.db.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString(exclude = "fornecedor")
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "preco_compra", precision = 18, scale = 2)
    private BigDecimal precoCompra;

    @Column(name = "preco_venda", precision = 18, scale = 2)
    private BigDecimal precoVenda;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @CreatedDate
    @Column(name = "data_cadastro")
    private LocalDateTime dataCadastro;

    @LastModifiedDate
    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;
}
