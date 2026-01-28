package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.mesa.Mesa;
import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRepository;
import br.com.passos.api_reserva_de_mesas.service.regrasNegocios.RegrasNegocioReserva;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private RegrasNegocioReserva regrasNegocioReserva;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private MesaService mesaService;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void deveCadastrarReservaComSucesso() {
        // 1. ARRANGE
        Long idMesa = 10L;
        Mesa mesa = new Mesa();
        mesa.setId(idMesa);

        Reserva reserva = new Reserva();
        reserva.setMesa(mesa);

        // Ensinamos o repositório a retornar a própria reserva quando salvar
        // (O método save sempre retorna o objeto salvo)
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        // NOTA: Não precisamos configurar o 'regrasNegocioReserva' nem o 'mesaService'
        // porque eles retornam VOID. O padrão do Mockito para void é "não fazer nada",
        // que é exatamente o que queremos no caso de sucesso (sem erros).

        // 2. ACT
        Reserva reservaSalva = reservaService.cadastrar(reserva);

        // 3. ASSERT
        // Verificamos o retorno
        assertEquals(reserva, reservaSalva);

        // AQUI É NOVO: Verificamos se o Service chamou os métodos necessários

        // Verificamos se as regras de validação foram chamadas 1 vez com essa reserva
        verify(regrasNegocioReserva, times(1)).regrasValidacaoReserva(reserva);

        // Verificamos se a atualização de status da mesa foi chamada 1 vez com o ID correto
        verify(mesaService, times(1)).atualizarStatusMesa(idMesa);

        // Verificamos se o repositório foi chamado para salvar
        verify(reservaRepository, times(1)).save(reserva);

    }

    @Test
    void naoDeveSalvarQuandoRegrasDeNegocioFalharem() {
        // 1. ARRANGE
        Reserva reserva = new Reserva();

        // Simulamos que a validação vai falhar (lançar exceção)
        // O comando doThrow é usado para métodos void que dão erro
        doThrow(new RuntimeException("Erro de validação"))
                .when(regrasNegocioReserva).regrasValidacaoReserva(reserva);

        // 2. ACT & 3. ASSERT
        assertThrows(RuntimeException.class, () -> {
            reservaService.cadastrar(reserva);
        });

        // VERIFICAÇÃO DE SEGURANÇA:
        // Garantimos que o save NUNCA foi chamado.
        // Se a validação falhou, não pode salvar no banco!
        verify(reservaRepository, never()).save(reserva);
        verify(mesaService, never()).atualizarStatusMesa(anyLong());
    }

}