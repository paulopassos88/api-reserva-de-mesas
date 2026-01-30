package br.com.passos.api_reserva_de_mesas.service.regrasNegocios;

import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaRepository;
import br.com.passos.api_reserva_de_mesas.domain.mesa.Status;
import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import br.com.passos.api_reserva_de_mesas.service.exception.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class RegrasNegocioReservaImpl implements RegrasNegocioReserva {

    private final MesaRepository mesaRepository;
    private final ReservaRepository reservaRepository;

    public RegrasNegocioReservaImpl(MesaRepository mesaRepository, ReservaRepository reservaRepository) {
        this.mesaRepository = mesaRepository;
        this.reservaRepository = reservaRepository;
    }

    @Override
    public void regrasValidacaoReserva(Reserva reserva) {
        Long mesaId = reserva.getMesa().getId();

        validarExistenciaEDisponibilidade(mesaId);
        validarHorarioDisponivel(reserva.getDataReserva(), mesaId);
        validarCapacidade(reserva.getQuantidadePessoas(), mesaId);
    }

    @Override
    public void validarCancelamento(Reserva reserva) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataReserva = reserva.getDataReserva();

        if (dataReserva.isBefore(agora)) {
            throw new CancelamentoNaoPermitidoException(
                    "Não é possível cancelar uma reserva que já ocorreu."
            );
        }

        long horasAteReserva = Duration.between(agora, dataReserva).toHours();

        if (horasAteReserva < 2) {
            throw new CancelamentoNaoPermitidoException(
                    "Não é possível cancelar a reserva com menos de 2 horas de antecedência."
            );
        }
    }


    private void validarExistenciaEDisponibilidade(long mesaId) {
        if (!mesaRepository.existsMesaBy(mesaId)) {
            throw new MesaNaoCadastradaException("Mesa não encontrada");
        }

        Status status = mesaRepository.statusMesa(mesaId);

        if (status == Status.RESERVADO || status == Status.INATIVA) {
            throw new MesaIndisponivelException("A mesa selecionada já está reservada ou encontra-se inativa.");
        }
    }

    private void validarHorarioDisponivel(LocalDateTime dataHorario, long idMesa) {
        if (reservaRepository.existeReservaNaData(dataHorario, idMesa)) {
            throw new DataHorarioIndisponivelException("Já existe uma reserva para esta mesa no horário selecionado.");
        }
    }

    private void validarCapacidade(int quantidadePessoas, long id) {
        int capacidade = mesaRepository.capacidadeMesa(id);

        if (quantidadePessoas > capacidade) {
            throw new CapacidadeExcedeException("A quantidade de pessoas excede a capacidade máxima da mesa (" + capacidade + ").");
        }
    }

}
