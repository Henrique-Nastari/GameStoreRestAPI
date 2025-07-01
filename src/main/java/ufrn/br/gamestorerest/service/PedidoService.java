package ufrn.br.gamestorerest.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufrn.br.gamestorerest.dto.PedidoRequestDTO;
import ufrn.br.gamestorerest.dto.PedidoResponseDTO;
import ufrn.br.gamestorerest.model.Pedido;
import ufrn.br.gamestorerest.model.Produto;
import ufrn.br.gamestorerest.model.Usuario;
import ufrn.br.gamestorerest.repository.PedidoRepository;
import ufrn.br.gamestorerest.repository.ProdutoRepository;
import ufrn.br.gamestorerest.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public PedidoResponseDTO criar(PedidoRequestDTO dto) {
        // 1. Pega o usuário autenticado a partir do contexto de segurança
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        // 2. Busca todos os produtos da lista de IDs
        Set<Produto> produtos = new HashSet<>(produtoRepository.findAllById(dto.produtoIds()));
        if(produtos.size() != dto.produtoIds().size()){
            throw new EntityNotFoundException("Um ou mais produtos não foram encontrados.");
        }

        // 3. Calcula o valor total do pedido
        BigDecimal valorTotal = produtos.stream()
                .map(Produto::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. Cria e salva o novo pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProdutos(produtos);
        pedido.setValorTotal(valorTotal);

        pedidoRepository.save(pedido);

        return new PedidoResponseDTO(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidosDoUsuarioLogado() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        return pedidoRepository.findByUsuario(usuario).stream()
                .map(PedidoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoDoUsuarioPorId(Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuario = usuarioRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + id + " não encontrado."));

        // Garante que o usuário só pode ver os próprios pedidos
        if (!pedido.getUsuario().equals(usuario)) {
            throw new SecurityException("Acesso negado: este pedido não pertence ao usuário logado.");
        }

        return new PedidoResponseDTO(pedido);
    }
}