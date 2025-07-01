package ufrn.br.gamestorerest.dto;

// DTO para enviar o token JWT como resposta de um login bem-sucedido.
public record TokenResponseDTO(
        String token
) {}