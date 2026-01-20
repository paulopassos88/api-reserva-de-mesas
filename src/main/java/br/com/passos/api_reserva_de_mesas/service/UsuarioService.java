package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.usuario.Usuario;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioRepository;
import br.com.passos.api_reserva_de_mesas.service.exception.EmailJaCadastradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        validarEmailUnico(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    private void validarEmailUnico(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new EmailJaCadastradoException("Email já cadastrado");
        }
    }

}
