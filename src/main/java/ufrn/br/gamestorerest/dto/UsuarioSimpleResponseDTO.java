package ufrn.br.gamestorerest.dto;

import ufrn.br.gamestorerest.model.Usuario;

public record UsuarioSimpleResponseDTO(
        String login
) {
    public UsuarioSimpleResponseDTO(Usuario usuario) {
        this(usuario.getLogin());
    }
}