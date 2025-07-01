package ufrn.br.gamestorerest.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufrn.br.gamestorerest.dto.ProdutoRequestDTO;
import ufrn.br.gamestorerest.dto.ProdutoResponseDTO;
import ufrn.br.gamestorerest.model.Categoria;
import ufrn.br.gamestorerest.model.Produto;
import ufrn.br.gamestorerest.repository.CategoriaRepository; // Importe o novo repositório
import ufrn.br.gamestorerest.repository.ProdutoRepository;

import java.time.LocalDateTime;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public Page<ProdutoResponseDTO> listarTodos(Pageable paginacao) {
        return repository.findAll(paginacao).map(ProdutoResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado."));
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO criar(ProdutoRequestDTO dto) {
        // 1. Busca a entidade Categoria pelo ID recebido no DTO
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + dto.categoriaId() + " não encontrada."));

        // 2. Cria o novo produto e associa a categoria encontrada
        Produto produto = new Produto();
        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());
        produto.setImageUrl(dto.imageUrl());
        produto.setCategoria(categoria); // Associa o objeto Categoria

        repository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado para atualização."));

        // Busca a categoria para garantir que ela existe
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoria com ID " + dto.categoriaId() + " não encontrada."));

        produto.setNome(dto.nome());
        produto.setDescricao(dto.descricao());
        produto.setPreco(dto.preco());
        produto.setEstoque(dto.estoque());
        produto.setImageUrl(dto.imageUrl());
        produto.setCategoria(categoria); // Atualiza a associação

        repository.save(produto);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public void deletar(Long id) {
        Produto produto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado para deleção."));
        produto.setIsDeleted(LocalDateTime.now());
        repository.save(produto);
    }
}