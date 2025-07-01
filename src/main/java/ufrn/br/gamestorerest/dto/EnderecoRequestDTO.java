package ufrn.br.gamestorerest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
        @NotBlank String logradouro,
        @NotBlank String bairro,
        @NotBlank @Size(min = 8, max = 8) String cep,
        @NotBlank String numero,
        String complemento,
        @NotBlank String cidade,
        @NotBlank @Size(min = 2, max = 2) String uf
) {}