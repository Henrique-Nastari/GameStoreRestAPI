package ufrn.br.gamestorerest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistroDTO(
        @NotBlank
        String login,

        @NotBlank
        String senha,

        @NotNull
        Boolean isAdmin
) {}