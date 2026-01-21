package br.com.passos.api_reserva_de_mesas.service.exception;

public class MesaIndisponivelException extends RuntimeException {
    public MesaIndisponivelException(String message) {
        super(message);
    }
}
