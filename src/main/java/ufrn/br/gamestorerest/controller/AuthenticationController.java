package ufrn.br.gamestorerest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ufrn.br.gamestorerest.dto.LoginRequestDTO;
import ufrn.br.gamestorerest.dto.RegistroDTO;
import ufrn.br.gamestorerest.dto.TokenResponseDTO;
import ufrn.br.gamestorerest.model.Usuario;
import ufrn.br.gamestorerest.repository.UsuarioRepository;
import ufrn.br.gamestorerest.service.TokenService;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        var userDetails = (UserDetails) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken(userDetails);

        return ResponseEntity.ok(new TokenResponseDTO(tokenJWT));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Void> registrar(@RequestBody @Valid RegistroDTO dto) {
        if (this.usuarioRepository.findByLogin(dto.login()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // Passa 'null' como último argumento para o novo campo 'endereco'.
        var novoUsuario = new Usuario(null, dto.login(), passwordEncoder.encode(dto.senha()), dto.isAdmin(), null);
        this.usuarioRepository.save(novoUsuario);

        return ResponseEntity.ok().build();
    }
}