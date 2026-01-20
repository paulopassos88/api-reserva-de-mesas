package br.com.passos.api_reserva_de_mesas.domain.reserva;

import br.com.passos.api_reserva_de_mesas.domain.mesa.Mesa;
import br.com.passos.api_reserva_de_mesas.domain.usuario.Usuario;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaRequest(

        @NotNull(message = "Usuário é obrigatório")
        Usuario usuario,

        @NotNull(message = "Mesa é obrigatório")
        Mesa mesa,

        @NotNull(message = "Data e hora da reserva não informada")
        LocalDateTime dataReserva,

        @NotNull(message = "Quantidade de pessoas não informada")
        Integer quantidadePessoas,

        @NotNull(message = "Status da reseva é obrigatório")
        Status status
) {
}
