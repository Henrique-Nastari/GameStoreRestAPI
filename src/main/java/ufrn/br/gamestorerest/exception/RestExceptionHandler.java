package ufrn.br.gamestorerest.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ufrn.br.gamestorerest.dto.ApiErrorDTO;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> tratarErro404(EntityNotFoundException ex, HttpServletRequest request) {
        ApiErrorDTO erro = new ApiErrorDTO(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    // TRATA ERROS DE PERMISSÃO
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ApiErrorDTO> tratarErro403(SecurityException ex, HttpServletRequest request) {
        ApiErrorDTO erro = new ApiErrorDTO(
                request.getRequestURI(),
                ex.getMessage(),
                HttpStatus.FORBIDDEN, // Status 403
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
    }
}