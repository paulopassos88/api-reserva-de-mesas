package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.mesa.Status;
import br.com.passos.api_reserva_de_mesas.domain.reserva.CancelamentoRequest;
import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import br.com.passos.api_reserva_de_mesas.service.exception.CancelamentoNaoPermitidoException;
import br.com.passos.api_reserva_de_mesas.service.regrasNegocios.RegrasNegocioReserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public Page<Reserva> listarReservas(int pagina, int tamanho) {
        Pageable pageable = PageRequest.of(pagina, tamanho, Sort.by("dataReserva").descending());
        return  reservaRepository.findAll(pageable);
    }

    @Transactional
    public void cancelarReserva(Long idUsuario, Long idReserva, String motivoCancelamento) {
        Reserva reserva = reservaRepository
                .buscarReservaPorIdEUsuario(idReserva, idUsuario)
                .orElseThrow(() -> new CancelamentoNaoPermitidoException(
                        "Reserva não encontrada ou não pertence ao usuário."));

        regrasNegocioReserva.validarCancelamento(reserva);

        reserva.cancelar(motivoCancelamento);

        mesaService.atualizarStatusMesa(reserva.getMesa().getId(), Status.DISPONIVEL);
    }

}

    /*

    public List<Reserva> listarReservas() {
        List<Reserva> reservas = reservaRepository.findAll();
        return reservas
                .stream()
                .sorted(Comparator.comparing(Reserva::getDataReserva, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
                .collect(Collectors.toList());
    }
     */
