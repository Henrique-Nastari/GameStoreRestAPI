package ufrn.br.gamestorerest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ufrn.br.gamestorerest.model.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    // Por enquanto, não precisamos de nenhum métod0 customizado aqui.
    // O JpaRepository já nos dá tudo o que precisamos para um CRUD básico.
}