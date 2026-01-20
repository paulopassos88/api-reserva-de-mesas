package br.com.passos.api_reserva_de_mesas.service.exception;

public class IdentificadorJaCadastradoException extends RuntimeException {
    public IdentificadorJaCadastradoException(String message) {
        super(message);
    }
}
