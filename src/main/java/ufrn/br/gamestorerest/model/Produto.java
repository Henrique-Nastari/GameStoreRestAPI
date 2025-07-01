package ufrn.br.gamestorerest.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted IS NULL")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do produto não pode ser vazio ou nulo.")
    @Size(min = 3, max = 255, message = "O nome do produto deve ter entre 3 e 255 caracteres.")
    @Column(nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "A descrição não pode ser vazia ou nula.")
    @Size(max = 1000, message = "A descrição não pode exceder 1000 caracteres.")
    @Column(nullable = false, length = 1000)
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @NotNull(message = "A quantidade em estoque não pode ser nula.")
    @PositiveOrZero(message = "O estoque não pode ser um número negativo.")
    @Column(nullable = false)
    private Integer estoque;

    @NotBlank(message = "A URL da imagem é obrigatória.")
    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private LocalDateTime isDeleted;

    // Define a relação de Muitos-Para-Um (Muitos Produtos para Uma Categoria)
    @ManyToOne(fetch = FetchType.EAGER)
    // Define a coluna de junção (chave estrangeira) na tabela 'produtos'
    @JoinColumn(name = "categoria_id", nullable = false)
    // Evita o loop infinito na serialização JSON
    @JsonBackReference
    private Categoria categoria;
}