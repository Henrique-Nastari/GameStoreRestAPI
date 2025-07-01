package ufrn.br.gamestorerest.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProdutoRequestDTO(
        @NotBlank @Size(min = 3, max = 255)
        String nome,

        @NotBlank @Size(max = 1000)
        String descricao,

        @NotNull @Positive
        BigDecimal preco,

        @NotNull(message = "O ID da categoria é obrigatório.")
        Long categoriaId,

        @NotNull @PositiveOrZero
        Integer estoque,

        @NotBlank
        String imageUrl
) {}