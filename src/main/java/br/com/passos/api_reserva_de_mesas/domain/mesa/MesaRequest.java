package br.com.passos.api_reserva_de_mesas.domain.mesa;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MesaRequest(

        @NotBlank(message = "O identificador da mesa é obrigatório")
        String identificador,

        @NotNull(message = "A capacidade da mesa é obrigatório")
        Integer capacidade,

        @NotNull(message = "Status é obrigatório")
        Status status
) {
}
