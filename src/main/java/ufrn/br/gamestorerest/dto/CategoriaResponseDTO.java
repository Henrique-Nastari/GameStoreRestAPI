package ufrn.br.gamestorerest.dto;

import ufrn.br.gamestorerest.model.Categoria;

public record CategoriaResponseDTO(
        Long id,
        String nome
) {
    // Construtor de conveniência para facilitar a conversão da entidade para o DTO
    public CategoriaResponseDTO(Categoria categoria) {
        this(categoria.getId(), categoria.getNome());
    }
}