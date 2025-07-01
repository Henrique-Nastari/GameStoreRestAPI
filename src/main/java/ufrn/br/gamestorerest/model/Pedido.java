package ufrn.br.gamestorerest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Anotação para que o Hibernate preencha a data automaticamente na criação.
    @CreationTimestamp
    private LocalDateTime dataPedido;

    private BigDecimal valorTotal;

    // --- RELACIONAMENTO COM USUÁRIO (Muitos Pedidos para Um Usuário) ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    // --- RELACIONAMENTO COM PRODUTO (Muitos Pedidos para Muitos Produtos) ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "pedido_produtos", // Nome da nova "tabela de junção"
            joinColumns = @JoinColumn(name = "pedido_id"), // Coluna que referencia esta entidade (Pedido)
            inverseJoinColumns = @JoinColumn(name = "produto_id") // Coluna que referencia a outra entidade (Produto)
    )
    private Set<Produto> produtos = new HashSet<>();
}