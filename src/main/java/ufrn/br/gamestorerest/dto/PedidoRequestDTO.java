package ufrn.br.gamestorerest.dto;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record PedidoRequestDTO(
        @NotEmpty(message = "A lista de produtos não pode ser vazia.")
        List<Long> produtoIds
) {}