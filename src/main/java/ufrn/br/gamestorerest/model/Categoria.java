package ufrn.br.gamestorerest.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da categoria não pode ser vazio.")
    @Column(nullable = false, unique = true)
    private String nome;

    // 1. ANOTAÇÃO DO RELACIONAMENTO
    // Uma Categoria tem Muitos Produtos.
    // "mappedBy = "categoria"" diz ao JPA: "A responsabilidade por gerenciar esta relação
    // (a coluna de chave estrangeira) está na classe Produto, no campo 'categoria'".
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    // 2. ANOTAÇÃO PARA EVITAR LOOP INFINITO NO JSON
    // Evita que, ao serializar Categoria, ele serialize Produtos, que por sua vez
    // serializariam a Categoria de volta, infinitamente.
    @JsonManagedReference
    private List<Produto> produtos = new ArrayList<>();
}