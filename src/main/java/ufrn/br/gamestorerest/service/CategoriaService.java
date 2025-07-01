package ufrn.br.gamestorerest.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufrn.br.gamestorerest.dto.CategoriaRequestDTO;
import ufrn.br.gamestorerest.dto.CategoriaResponseDTO;
import ufrn.br.gamestorerest.model.Categoria;
import ufrn.br.gamestorerest.repository.CategoriaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Transactional
    public CategoriaResponseDTO criar(CategoriaRequestDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.nome());
        repository.save(categoria);
        return new CategoriaResponseDTO(categoria);
    }

    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarTodas() {
        return repository.findAll()
                .stream()
                .map(CategoriaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoriaResponseDTO buscarPorId(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada."));
        return new CategoriaResponseDTO(categoria);
    }

    @Transactional
    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + id + " não encontrada para atualização."));

        categoria.setNome(dto.nome());
        repository.save(categoria);

        return new CategoriaResponseDTO(categoria);
    }

    @Transactional
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Categoria com ID " + id + " não encontrada para deleção.");
        }
        // CUIDADO: Em uma aplicação real, antes de deletar uma categoria,
        // seria preciso verificar se não existem produtos associados a ela.
        // Para nosso projeto, uma deleção direta é suficiente por enquanto.
        repository.deleteById(id);
    }
}