package ufrn.br.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufrn.br.gamestorerest.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Graças à anotação @Where na entidade Produto, métodos como findAll() e findById()
    // já irão buscar apenas os produtos onde 'is_deleted' é nulo.
    // Não precisamos mais do métod0 findByIsDeletedIsNull() que tínhamos no projeto MVC.
}