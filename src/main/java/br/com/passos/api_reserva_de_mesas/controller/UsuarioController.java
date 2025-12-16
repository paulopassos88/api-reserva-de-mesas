package br.com.passos.api_reserva_de_mesas.controller;

import br.com.passos.api_reserva_de_mesas.domain.usuario.Usuario;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioMapper;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioRequest;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioResponse;
import br.com.passos.api_reserva_de_mesas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    public UsuarioController(UsuarioService usuarioService, UsuarioMapper usuarioMapper) {
        this.usuarioService = usuarioService;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping(value = "/registrar")
    public ResponseEntity<UsuarioResponse> cadastrar(@Valid @RequestBody UsuarioRequest usuarioRequest) {
        Usuario usuario = usuarioMapper.toModel(usuarioRequest);
        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        UsuarioResponse usuarioResponse = usuarioMapper.toResponseDTO(novoUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
    }
}
