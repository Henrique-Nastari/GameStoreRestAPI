package ufrn.br.gamestorerest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import ufrn.br.gamestorerest.dto.PedidoRequestDTO;
import ufrn.br.gamestorerest.dto.PedidoResponseDTO;
import ufrn.br.gamestorerest.service.PedidoService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public ResponseEntity<EntityModel<PedidoResponseDTO>> criar(@RequestBody @Valid PedidoRequestDTO dto, UriComponentsBuilder uriBuilder) {
        PedidoResponseDTO pedido = service.criar(dto);
        EntityModel<PedidoResponseDTO> entityModel = EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).buscarMeuPedidoPorId(pedido.id())).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarMeusPedidos()).withRel("meus-pedidos"));

        URI uri = uriBuilder.path("/pedidos/{id}").buildAndExpand(pedido.id()).toUri();
        return ResponseEntity.created(uri).body(entityModel);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<PedidoResponseDTO>>> listarMeusPedidos() {
        List<EntityModel<PedidoResponseDTO>> pedidos = service.listarPedidosDoUsuarioLogado().stream()
                .map(pedido -> EntityModel.of(pedido,
                        linkTo(methodOn(PedidoController.class).buscarMeuPedidoPorId(pedido.id())).withSelfRel()))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<PedidoResponseDTO>> collectionModel = CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoController.class).listarMeusPedidos()).withSelfRel());

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PedidoResponseDTO>> buscarMeuPedidoPorId(@PathVariable Long id) {
        PedidoResponseDTO pedido = service.buscarPedidoDoUsuarioPorId(id);
        EntityModel<PedidoResponseDTO> entityModel = EntityModel.of(pedido,
                linkTo(methodOn(PedidoController.class).buscarMeuPedidoPorId(id)).withSelfRel(),
                linkTo(methodOn(PedidoController.class).listarMeusPedidos()).withRel("meus-pedidos"));

        return ResponseEntity.ok(entityModel);
    }
}