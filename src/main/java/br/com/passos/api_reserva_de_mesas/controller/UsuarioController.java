package br.com.passos.api_reserva_de_mesas.controller;

import br.com.passos.api_reserva_de_mesas.domain.usuario.Usuario;
import br.com.passos.api_reserva_de_mesas.service.UsuarioService;
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

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping(value = "/registrar")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}
