package ufrn.br.gamestorerest.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ufrn.br.gamestorerest.dto.EnderecoRequestDTO;
import ufrn.br.gamestorerest.dto.UsuarioResponseDTO;
import ufrn.br.gamestorerest.model.Endereco;
import ufrn.br.gamestorerest.model.Usuario;
import ufrn.br.gamestorerest.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    // Busca o usuário atualmente logado
    private Usuario getUsuarioLogado() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return repository.findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarMeusDados() {
        Usuario usuario = getUsuarioLogado();
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO adicionarOuAtualizarEndereco(EnderecoRequestDTO dto) {
        Usuario usuario = getUsuarioLogado();

        // Verifica se o usuário já tem um endereço para atualizá-lo,
        Endereco endereco = usuario.getEndereco();
        if (endereco == null) {
            endereco = new Endereco();
        }

        // Mapeia os dados do DTO para a entidade Endereco
        endereco.setLogradouro(dto.logradouro());
        endereco.setBairro(dto.bairro());
        endereco.setCep(dto.cep());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setCidade(dto.cidade());
        endereco.setUf(dto.uf());

        // Associa o endereço ao usuário
        usuario.setEndereco(endereco);

        // Salva o usuário. Por causa do 'cascade = CascadeType.ALL',
        // o endereço será salvo ou atualizado junto.
        repository.save(usuario);

        return new UsuarioResponseDTO(usuario);
    }
}