package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaRepository;
import br.com.passos.api_reserva_de_mesas.domain.mesa.Status;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidacoesReservaImpl implements ValidacoesReserva {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;

    public ValidacoesReservaImpl(MesaRepository mesaRepository, ReservaRepository reservaRepository) {
        this.mesaRepository = mesaRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void mesaDisponivel(long mesaId) {
        Status status = mesaRepository.statusMesa(mesaId)
                .orElseThrow(() -> new RuntimeException("Mesa não encontrada"));

        if (status == Status.RESERVADO || status == Status.INATIVA) {
            throw new RuntimeException("Mesa indisponível");
        }
    }

    @Override
    public void horarioDisponivel(LocalDateTime dataHorario) {
        if (reservaRepository.existeReservaNaData(dataHorario)) {
            throw new RuntimeException("A data e hora indisponível no momento");
        }
    }
}
