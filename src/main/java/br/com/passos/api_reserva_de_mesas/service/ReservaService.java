package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import br.com.passos.api_reserva_de_mesas.service.exception.CancelamentoNaoPermitidoException;
import br.com.passos.api_reserva_de_mesas.service.regrasNegocios.RegrasNegocioReserva;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static br.com.passos.api_reserva_de_mesas.domain.mesa.Status.DISPONIVEL;
import static br.com.passos.api_reserva_de_mesas.domain.mesa.Status.RESERVADO;

@Service
public class ReservaService {

    private final RegrasNegocioReserva regrasNegocioReserva;
    private final ReservaRepository reservaRepository;
    private final MesaService mesaService;

    public ReservaService(RegrasNegocioReserva regrasNegocioReserva, ReservaRepository reservaRepository, MesaService mesaService) {
        this.regrasNegocioReserva = regrasNegocioReserva;
        this.reservaRepository = reservaRepository;
        this.mesaService = mesaService;
    }

    @Transactional
    public Reserva cadastrar(Reserva reserva) {
        regrasNegocioReserva.regrasValidacaoReserva(reserva);

        mesaService.atualizarStatusMesa(reserva.getMesa().getId(), RESERVADO);

        return reservaRepository.save(reserva);
    }

    @Transactional
    public Reserva cancelarReserva(Reserva reserva) {

        Reserva reservaExistente = reservaRepository
                .buscarReservarAtiva(
                        reserva.getUsuario().getId(),
                        reserva.getDataReserva()
                )
                .orElseThrow(() -> new CancelamentoNaoPermitidoException("Reserva não encontrada"));

        regrasNegocioReserva.validarCancelamento(reservaExistente);

        reservaExistente.setAtiva(false);

        mesaService.atualizarStatusMesa(
                reservaExistente.getMesa().getId(),
                DISPONIVEL
        );

        return reservaExistente;
    }

}
