package ufrn.br.gamestorerest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufrn.br.gamestorerest.dto.CategoriaRequestDTO;
import ufrn.br.gamestorerest.dto.CategoriaResponseDTO;
import ufrn.br.gamestorerest.service.CategoriaService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @PostMapping
    public ResponseEntity<EntityModel<CategoriaResponseDTO>> criar(@RequestBody @Valid CategoriaRequestDTO dto, UriComponentsBuilder uriBuilder) {
        CategoriaResponseDTO categoria = service.criar(dto);
        EntityModel<CategoriaResponseDTO> entityModel = EntityModel.of(categoria,
                linkTo(methodOn(CategoriaController.class).buscarPorId(categoria.id())).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).listar()).withRel("todas-categorias"));

        URI uri = uriBuilder.path("/categorias/{id}").buildAndExpand(categoria.id()).toUri();
        return ResponseEntity.created(uri).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CategoriaResponseDTO>>> listar() {
        List<EntityModel<CategoriaResponseDTO>> categorias = service.listarTodas().stream()
                .map(categoria -> EntityModel.of(categoria,
                        linkTo(methodOn(CategoriaController.class).buscarPorId(categoria.id())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<CategoriaResponseDTO>> collectionModel = CollectionModel.of(categorias,
                linkTo(methodOn(CategoriaController.class).listar()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaResponseDTO>> buscarPorId(@PathVariable Long id) {
        CategoriaResponseDTO categoria = service.buscarPorId(id);
        EntityModel<CategoriaResponseDTO> entityModel = EntityModel.of(categoria,
                linkTo(methodOn(CategoriaController.class).buscarPorId(id)).withSelfRel(),
                linkTo(methodOn(CategoriaController.class).listar()).withRel("todas-categorias"));

        return ResponseEntity.ok(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategoriaResponseDTO>> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dto) {
        CategoriaResponseDTO categoria = service.atualizar(id, dto);
        EntityModel<CategoriaResponseDTO> entityModel = EntityModel.of(categoria,
                linkTo(methodOn(CategoriaController.class).buscarPorId(id)).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}