package br.com.passos.api_reserva_de_mesas.domain.mesa;

public record MesaResponse(
        Long id,
        String identificador,
        Integer capacidade,
        Status status
) {
}
