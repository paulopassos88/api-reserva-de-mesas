package br.com.passos.api_reserva_de_mesas.controller;

import br.com.passos.api_reserva_de_mesas.domain.reserva.Reserva;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaMapper;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaRequest;
import br.com.passos.api_reserva_de_mesas.domain.reserva.ReservaResponse;
import br.com.passos.api_reserva_de_mesas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserva")
public class ReservaController {

    private final ReservaService reservaService;
    private final ReservaMapper reservaMapper;

    public ReservaController(ReservaService reservaService, ReservaMapper reservaMapper) {
        this.reservaService = reservaService;
        this.reservaMapper = reservaMapper;
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> cadastrar(@Valid @RequestBody ReservaRequest reservaRequest) {
        Reserva reserva = reservaMapper.toModel(reservaRequest);
        Reserva novaReserva = reservaService.cadastrar(reserva);
        ReservaResponse reservaResponse = reservaMapper.toResponseDTO(novaReserva);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaResponse);
    }
}
