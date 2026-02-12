package br.com.passos.api_reserva_de_mesas.controller;

import br.com.passos.api_reserva_de_mesas.domain.reserva.*;
import br.com.passos.api_reserva_de_mesas.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<Page<ReservaResponse>> listar(@RequestParam(defaultValue = "0") int pagina,
                                                        @RequestParam(defaultValue = "5") int tamanho){
        Page<Reserva> reservas = reservaService.listarReservas(pagina, tamanho);
        Page<ReservaResponse> reservaResponses = reservas.map(reservaMapper::toResponseDTO);
        return ResponseEntity.status(HttpStatus.OK).body(reservaResponses);
    }

    @PostMapping("/{idUsuario}/reservas/{idReserva}/cancelar")
    public ResponseEntity<Void> cancelarReserva(
            @PathVariable Long idUsuario,
            @PathVariable Long idReserva,
            @RequestBody CancelamentoRequest request
    ) {
        reservaService.cancelarReserva(idUsuario, idReserva, request.motivoCancelamento());
        return ResponseEntity.noContent().build();
    }

}
