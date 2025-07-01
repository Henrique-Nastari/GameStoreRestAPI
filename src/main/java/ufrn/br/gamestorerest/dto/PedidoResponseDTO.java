package ufrn.br.gamestorerest.dto;

import ufrn.br.gamestorerest.model.Pedido;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record PedidoResponseDTO(
        Long id,
        LocalDateTime dataPedido,
        BigDecimal valorTotal,
        UsuarioSimpleResponseDTO usuario,
        Set<ProdutoResponseDTO> produtos
) {
    public PedidoResponseDTO(Pedido pedido) {
        this(
                pedido.getId(),
                pedido.getDataPedido(),
                pedido.getValorTotal(),
                new UsuarioSimpleResponseDTO(pedido.getUsuario()),
                pedido.getProdutos().stream()
                        .map(ProdutoResponseDTO::new)
                        .collect(Collectors.toSet())
        );
    }
}