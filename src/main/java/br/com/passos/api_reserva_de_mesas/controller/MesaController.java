package br.com.passos.api_reserva_de_mesas.controller;

import br.com.passos.api_reserva_de_mesas.domain.mesa.Mesa;
import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaMapper;
import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaRequest;
import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaResponse;
import br.com.passos.api_reserva_de_mesas.service.MesaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    private final MesaService mesaService;
    private final MesaMapper mesaMapper;

    public MesaController(MesaService mesaService, MesaMapper mesaMapper) {
        this.mesaService = mesaService;
        this.mesaMapper = mesaMapper;
    }

    @PostMapping
    public ResponseEntity<MesaResponse> cadastrar(@Valid @RequestBody MesaRequest mesaRequest) {
        Mesa mesa = mesaMapper.toModel(mesaRequest);
        Mesa mesaSalva = mesaService.cadastrar(mesa);
        MesaResponse mesaResponse = mesaMapper.toResponseDTO(mesaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaResponse);
    }
}
