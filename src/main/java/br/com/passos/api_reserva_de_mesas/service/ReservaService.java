package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ValidacoesReserva validacoesReserva;
    private final ReservaRepository reservaRepository;

    public ReservaService(ValidacoesReserva validacoesReserva, ReservaRepository reservaRepository) {
        this.validacoesReserva = validacoesReserva;
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva cadastrar(Reserva reserva) {
        validacoesReserva.mesaDisponivel(reserva.getMesa().getId());
        validacoesReserva.horarioDisponivel(reserva.getDataReserva());

        return reservaRepository.save(reserva);
    }
}
