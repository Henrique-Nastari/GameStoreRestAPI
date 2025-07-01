package ufrn.br.gamestorerest.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ufrn.br.gamestorerest.repository.UsuarioRepository;
import ufrn.br.gamestorerest.service.TokenService;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Recupera o token do cabeçalho da requisição
        var tokenJWT = recuperarToken(request);

        if (tokenJWT != null) {
            // 2. Valida o token e extrai o login do usuário
            var subject = tokenService.getSubject(tokenJWT);

            // 3. Carrega os detalhes do usuário do banco de dados
            UserDetails usuario = userDetailsService.loadUserByUsername(subject);

            // 4. Cria um objeto de autenticação para o Spring Security
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            // 5. Define o usuário como autenticado no contexto de segurança do Spring
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua a cadeia de filtros
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }
}