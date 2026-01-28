package br.com.passos.api_reserva_de_mesas.service.regrasNegocios;

import br.com.passos.api_reserva_de_mesas.domain.mesa.Mesa;
import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaRepository;
import br.com.passos.api_reserva_de_mesas.domain.mesa.Status;
import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import br.com.passos.api_reserva_de_mesas.service.exception.CapacidadeExcedeException;
import br.com.passos.api_reserva_de_mesas.service.exception.DataHorarioIndisponivelException;
import br.com.passos.api_reserva_de_mesas.service.exception.MesaIndisponivelException;
import br.com.passos.api_reserva_de_mesas.service.exception.MesaNaoCadastradaException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegrasNegocioReservaImplTest {

    @Mock
    private MesaRepository mesaRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private RegrasNegocioReservaImpl regrasNegocioReserva;

    @Test
    void deveValidarReservaComSucessoQuandoTudoEstiverCorreto() {
        // 1 - Arrange (preparar)
        Long idMesa = 1L;
        Mesa mesa = new Mesa();
        mesa.setId(idMesa);

        Reserva reserva = new Reserva();
        reserva.setMesa(mesa);
        reserva.setDataReserva(LocalDateTime.now().plusDays(1));
        reserva.setQuantidadePessoas(4);

        // 2 - Act (Agir)
        when(mesaRepository.existsMesaBy(idMesa)).thenReturn(true);
        when(mesaRepository.statusMesa(idMesa)).thenReturn(Status.DISPONIVEL);
        when(mesaRepository.capacidadeMesa(idMesa)).thenReturn(4);

        when(reservaRepository.existeReservaNaData(reserva.getDataReserva(), idMesa)).thenReturn(false);

        // 2 - Act (Agir) 3 - Assert (validar)
        assertDoesNotThrow(() -> regrasNegocioReserva.regrasValidacaoReserva(reserva));
    }

    @Test
    void deveLancarExceptionQuandoMesaNaoExiste() {
        // 1. ARRANGE
        Long idMesa = 99L;
        Reserva reserva = new Reserva();
        Mesa mesa = new Mesa();
        mesa.setId(idMesa);
        reserva.setMesa(mesa);

        // Ensinamos o mock a dizer que NÃO existe
        when(mesaRepository.existsMesaBy(idMesa)).thenReturn(false);

        // 2. ACT & 3. ASSERT
        // Verificamos se a classe MesaNaoCadastradaException foi lançada
        assertThrows(MesaNaoCadastradaException.class, () -> {
            regrasNegocioReserva.regrasValidacaoReserva(reserva);
        });
    }

    @Test
    void deveLancarExceptionQuandoMesaEstiverReservada() {
        // 1. ARRANGE
        Long idMesa = 1L;
        Reserva reserva = new Reserva();
        Mesa mesa = new Mesa();
        mesa.setId(idMesa);
        reserva.setMesa(mesa);

        // Precisamos passar da primeira validação (existe)
        when(mesaRepository.existsMesaBy(idMesa)).thenReturn(true);

        // Para falhar na segunda validação (status)
        when(mesaRepository.statusMesa(idMesa)).thenReturn(Status.RESERVADO);

        // 2. ACT & 3. ASSERT
        assertThrows(MesaIndisponivelException.class, () -> {
            regrasNegocioReserva.regrasValidacaoReserva(reserva);
        });
    }

    @Test
    void deveLancarExceptionQuandoHorarioIndisponivel() {
        // 1. ARRANGE
        Long idMesa = 1L;
        LocalDateTime dataReserva = LocalDateTime.now();

        Reserva reserva = new Reserva();
        Mesa mesa = new Mesa();
        mesa.setId(idMesa);
        reserva.setMesa(mesa);
        reserva.setDataReserva(dataReserva);

        // Passa nas validações de mesa
        when(mesaRepository.existsMesaBy(idMesa)).thenReturn(true);
        when(mesaRepository.statusMesa(idMesa)).thenReturn(Status.DISPONIVEL);

        // Falha na validação de horário (dizemos que JÁ EXISTE reserva)
        when(reservaRepository.existeReservaNaData(dataReserva, idMesa)).thenReturn(true);

        // 2. ACT & 3. ASSERT
        assertThrows(DataHorarioIndisponivelException.class, () -> {
            regrasNegocioReserva.regrasValidacaoReserva(reserva);
        });
    }

    @Test
    void deveLancarExceptionQuandoQuantidadePessoasExcedeCapacidade() {
        // 1. ARRANGE
        Long idMesa = 1L;

        // Configurando a Reserva (DADOS REAIS, não mock)
        Reserva reserva = new Reserva();
        Mesa mesa = new Mesa();
        mesa.setId(idMesa);
        reserva.setMesa(mesa);
        reserva.setDataReserva(LocalDateTime.now().plusDays(1));

        // AQUI ESTÁ O SEGREDO: Definimos um número alto de pessoas
        reserva.setQuantidadePessoas(6);

        // Configurando os Mocks (COMPORTAMENTO)
        // Passa pela checagem de existência
        when(mesaRepository.existsMesaBy(idMesa)).thenReturn(true);
        // Passa pela checagem de status
        when(mesaRepository.statusMesa(idMesa)).thenReturn(Status.DISPONIVEL);
        // Passa pela checagem de horário
        when(reservaRepository.existeReservaNaData(reserva.getDataReserva(), idMesa)).thenReturn(false);

        // Falha na checagem de capacidade:
        // O banco "diz" que cabe 5, mas a reserva tem 6
        when(mesaRepository.capacidadeMesa(idMesa)).thenReturn(5);

        // 2. ACT & 3. ASSERT
        assertThrows(CapacidadeExcedeException.class, () -> {
            regrasNegocioReserva.regrasValidacaoReserva(reserva);
        });
    }
}