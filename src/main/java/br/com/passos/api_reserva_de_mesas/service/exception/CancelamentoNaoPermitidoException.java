package br.com.passos.api_reserva_de_mesas.service.exception;

public class CancelamentoNaoPermitidoException extends RuntimeException {
    public CancelamentoNaoPermitidoException(String message) {
        super(message);
    }
}
