package br.com.passos.api_reserva_de_mesas.service.exception;

public class CapacidadeExcedeException extends RuntimeException {
    public CapacidadeExcedeException(String message) {
        super(message);
    }
}
