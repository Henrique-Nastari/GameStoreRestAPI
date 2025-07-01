package ufrn.br.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.br.gamestorerest.model.Pedido;
import ufrn.br.gamestorerest.model.Usuario;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Métod0 customizado para encontrar todos os pedidos de um determinado usuário
    List<Pedido> findByUsuario(Usuario usuario);

}