package br.com.passos.api_reserva_de_mesas.domain.usuario;

public record UsuarioResponse(

        Long id,
        String nome,
        String email,
        Role role
) {
}
