package ufrn.br.gamestorerest.dto;

import ufrn.br.gamestorerest.model.Produto;
import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,

        CategoriaResponseDTO categoria,

        String imageUrl
) {
    public ProdutoResponseDTO(Produto produto){
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),

                // Cria um DTO para a categoria aninhada
                new CategoriaResponseDTO(produto.getCategoria()),

                produto.getImageUrl()
        );
    }
}