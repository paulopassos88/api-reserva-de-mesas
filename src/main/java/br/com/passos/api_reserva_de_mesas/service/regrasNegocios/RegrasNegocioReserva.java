package br.com.passos.api_reserva_de_mesas.service.regrasNegocios;

import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import org.springframework.stereotype.Component;

@Component
public interface RegrasNegocioReserva {

    void regrasValidacaoReserva(Reserva reserva);

    void validarCancelamento(Reserva reserva);

}
