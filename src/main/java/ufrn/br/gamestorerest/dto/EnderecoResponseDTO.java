package ufrn.br.gamestorerest.dto;

import ufrn.br.gamestorerest.model.Endereco;

public record EnderecoResponseDTO(
        Long id,
        String logradouro,
        String bairro,
        String cep,
        String numero,
        String complemento,
        String cidade,
        String uf
) {
    public EnderecoResponseDTO(Endereco endereco) {
        this(
                endereco.getId(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getCep(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getUf()
        );
    }
}