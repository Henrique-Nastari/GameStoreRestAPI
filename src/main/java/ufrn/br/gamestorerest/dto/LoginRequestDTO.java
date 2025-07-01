package ufrn.br.gamestorerest.dto;

import jakarta.validation.constraints.NotBlank;

// DTO para receber as credenciais de login.
public record LoginRequestDTO(
        @NotBlank(message = "O campo 'login' não pode ser vazio.")
        String login,

        @NotBlank(message = "O campo 'senha' não pode ser vazio.")
        String senha
) {}