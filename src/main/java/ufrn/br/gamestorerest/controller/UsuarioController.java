package ufrn.br.gamestorerest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ufrn.br.gamestorerest.dto.EnderecoRequestDTO;
import ufrn.br.gamestorerest.dto.UsuarioResponseDTO;
import ufrn.br.gamestorerest.service.UsuarioService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/me")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> buscarMeusDados() {
        UsuarioResponseDTO usuario = service.buscarMeusDados();
        EntityModel<UsuarioResponseDTO> entityModel = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).buscarMeusDados()).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }

    @PostMapping("/me/endereco")
    public ResponseEntity<EntityModel<UsuarioResponseDTO>> adicionarOuAtualizarEndereco(@RequestBody @Valid EnderecoRequestDTO dto) {
        UsuarioResponseDTO usuario = service.adicionarOuAtualizarEndereco(dto);
        EntityModel<UsuarioResponseDTO> entityModel = EntityModel.of(usuario,
                linkTo(methodOn(UsuarioController.class).buscarMeusDados()).withSelfRel());

        return ResponseEntity.ok(entityModel);
    }
}