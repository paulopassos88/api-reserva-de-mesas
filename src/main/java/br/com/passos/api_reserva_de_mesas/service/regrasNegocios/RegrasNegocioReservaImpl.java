package br.com.passos.api_reserva_de_mesas.service.regrasNegocios;

import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaRepository;
import br.com.passos.api_reserva_de_mesas.domain.mesa.Status;
import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import br.com.passos.api_reserva_de_mesas.service.MesaService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RegrasNegocioReservaImpl implements RegrasNegocioReserva {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;
    private final MesaService mesaService;

    public RegrasNegocioReservaImpl(MesaRepository mesaRepository, ReservaRepository reservaRepository, MesaService mesaService) {
        this.mesaRepository = mesaRepository;
        this.reservaRepository = reservaRepository;
        this.mesaService = mesaService;
    }

    @Override
    public void regrasValidacaoReserva(Reserva reserva) {
        mesaDisponivel(reserva.getMesa().getId());
        horarioDisponivel(reserva.getDataReserva(), reserva.getMesa().getId());
        capacidadeMesa(reserva.getQuantidadePessoas(), reserva.getMesa().getId());

    }

    private void mesaDisponivel(long mesaId) {
        mesaService.mesaExiste(mesaId);
        Status status = mesaRepository.statusMesa(mesaId);

        if (status == Status.RESERVADO || status == Status.INATIVA) {
            throw new RuntimeException("Mesa indisponível");
        }
    }

    private void horarioDisponivel(LocalDateTime dataHorario, long idMesa) {
        if (reservaRepository.existeReservaNaData(dataHorario, idMesa)) {
            throw new RuntimeException("A data e hora indisponível no momento");
        }
    }

    private void capacidadeMesa(int quantidadePessoas, long id) {
        int capacidade = mesaRepository.capacidadeMesa(id);

        if (capacidade < quantidadePessoas) {
            throw new RuntimeException("A Quantidade pessoas maior do que a capacidade da mesa");
        }
    }

}
