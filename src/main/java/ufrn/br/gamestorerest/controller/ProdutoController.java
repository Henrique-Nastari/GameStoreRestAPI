package ufrn.br.gamestorerest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufrn.br.gamestorerest.dto.ProdutoRequestDTO;
import ufrn.br.gamestorerest.dto.ProdutoResponseDTO;
import ufrn.br.gamestorerest.service.ProdutoService;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping
    // O 'PagedResourcesAssembler' agora é um parâmetro do métod0.
    public ResponseEntity<PagedModel<EntityModel<ProdutoResponseDTO>>> listar(
            @PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao,
            PagedResourcesAssembler<ProdutoResponseDTO> assembler) {

        Page<ProdutoResponseDTO> paginaDeDto = service.listarTodos(paginacao);

        // O 'assembler' recebido como parâmetro é usado para converter a Page em PagedModel.
        PagedModel<EntityModel<ProdutoResponseDTO>> pagedModel = assembler
                .toModel(paginaDeDto, dto -> EntityModel.of(dto,
                        linkTo(methodOn(ProdutoController.class).buscarPorId(dto.id())).withSelfRel()));

        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO dto = service.buscarPorId(id);
        EntityModel<ProdutoResponseDTO> model = EntityModel.of(dto,
                linkTo(methodOn(ProdutoController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> criar(@RequestBody @Valid ProdutoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        ProdutoResponseDTO produtoCriado = service.criar(dto);
        EntityModel<ProdutoResponseDTO> model = EntityModel.of(produtoCriado,
                linkTo(methodOn(ProdutoController.class).buscarPorId(produtoCriado.id())).withSelfRel());

        URI uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produtoCriado.id()).toUri();
        return ResponseEntity.created(uri).body(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProdutoResponseDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid ProdutoRequestDTO dto) {
        ProdutoResponseDTO produtoAtualizado = service.atualizar(id, dto);
        EntityModel<ProdutoResponseDTO> model = EntityModel.of(produtoAtualizado,
                linkTo(methodOn(ProdutoController.class).buscarPorId(id)).withSelfRel());
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}