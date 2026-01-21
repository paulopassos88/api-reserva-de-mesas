package br.com.passos.api_reserva_de_mesas.service.exception;

public class MesaNaoCadastradaException extends RuntimeException {
    public MesaNaoCadastradaException(String message) {
        super(message);
    }
}
