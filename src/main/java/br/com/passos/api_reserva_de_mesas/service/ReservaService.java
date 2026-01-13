package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaService {

    private final ValidacoesReserva validacoesReserva;
    private final ReservaRepository reservaRepository;
    private final MesaService mesaService;

    public ReservaService(ValidacoesReserva validacoesReserva, ReservaRepository reservaRepository, MesaService mesaService) {
        this.validacoesReserva = validacoesReserva;
        this.reservaRepository = reservaRepository;
        this.mesaService = mesaService;
    }

    @Transactional
    public Reserva cadastrar(Reserva reserva) {
        validacoesReserva.mesaDisponivel(reserva.getMesa().getId());
        validacoesReserva.horarioDisponivel(reserva.getDataReserva());
        validacoesReserva.capacidadeMesa(reserva.getQuantidadePessoas(), reserva.getMesa().getId());

        mesaService.atualizarStatusMesa(reserva.getMesa().getId());

        return reservaRepository.save(reserva);
    }
}
