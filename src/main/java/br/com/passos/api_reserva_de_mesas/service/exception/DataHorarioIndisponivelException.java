package br.com.passos.api_reserva_de_mesas.service.exception;

public class DataHorarioIndisponivelException extends RuntimeException {
    public DataHorarioIndisponivelException(String message) {
        super(message);
    }
}
