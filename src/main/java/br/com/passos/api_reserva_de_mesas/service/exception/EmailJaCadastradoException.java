package br.com.passos.api_reserva_de_mesas.service.exception;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException(String message) {
        super(message);
    }
}
