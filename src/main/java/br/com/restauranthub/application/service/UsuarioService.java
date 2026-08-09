package br.com.restauranthub.application.service;

import br.com.restauranthub.api.dto.UsuarioRequest;
import br.com.restauranthub.api.dto.UsuarioResponse;
import br.com.restauranthub.api.exception.RecursoNaoEncontradoException;
import br.com.restauranthub.api.exception.RegraNegocioException;
import br.com.restauranthub.domain.entity.Usuario;
import br.com.restauranthub.domain.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(
            UsuarioRepository repository,
            PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UsuarioResponse> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public UsuarioResponse buscarPorId(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado."
                        )
                );

        return converterParaResponse(usuario);
    }

    public UsuarioResponse salvar(UsuarioRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new RegraNegocioException(
                    "E-mail já cadastrado."
            );
        }

        Usuario usuario = new Usuario();

        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        usuario.setRole(request.role());
        usuario.setConsentimentoLgpd(
                Boolean.TRUE.equals(request.consentimentoLgpd())
        );

        Usuario usuarioSalvo = repository.save(usuario);

        return converterParaResponse(usuarioSalvo);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado."
            );
        }

        repository.deleteById(id);
    }

    private UsuarioResponse converterParaResponse(Usuario usuario) {
        return new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole(),
                usuario.getConsentimentoLgpd(),
                usuario.getCreatedAt(),
                usuario.getUpdatedAt()
        );
    }
}