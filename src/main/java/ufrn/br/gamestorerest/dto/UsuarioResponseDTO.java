package ufrn.br.gamestorerest.dto;

import ufrn.br.gamestorerest.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String login,
        EnderecoResponseDTO endereco
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getLogin(),
                // Verifica se o endereço não é nulo antes de criar o DTO dele
                usuario.getEndereco() != null ? new EnderecoResponseDTO(usuario.getEndereco()) : null
        );
    }
}