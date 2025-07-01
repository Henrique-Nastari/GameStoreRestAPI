package ufrn.br.gamestorerest.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaRequestDTO(
        @NotBlank(message = "O nome da categoria é obrigatório.")
        String nome
) {}