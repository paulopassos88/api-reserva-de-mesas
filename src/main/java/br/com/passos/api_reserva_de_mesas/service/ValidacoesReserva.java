package br.com.passos.api_reserva_de_mesas.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public interface ValidacoesReserva {

    void mesaDisponivel(long mesa_id);

    void horarioDisponivel(LocalDateTime data_horario);

    void capacidadeMesa(int quantidadePessoas, long id);

}
