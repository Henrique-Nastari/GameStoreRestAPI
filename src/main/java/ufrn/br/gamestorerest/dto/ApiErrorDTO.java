package ufrn.br.gamestorerest.dto;

import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

/**
 * DTO para padronizar as respostas de erro da API.
 */
public record ApiErrorDTO(
        String path,
        String message,
        HttpStatus status,
        LocalDateTime timestamp
) {}