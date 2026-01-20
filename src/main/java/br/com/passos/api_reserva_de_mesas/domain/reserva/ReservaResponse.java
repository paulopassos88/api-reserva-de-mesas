package br.com.passos.api_reserva_de_mesas.domain.reserva;

import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaResponse;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioResponse;

import java.time.LocalDateTime;

public record ReservaResponse(

        Long id,
        UsuarioResponse usuario,
        MesaResponse mesa,
        LocalDateTime dataReserva,
        Integer quantidadePessoas,
        Status status
) {
}
