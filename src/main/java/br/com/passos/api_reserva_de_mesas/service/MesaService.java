package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.mesa.Mesa;
import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaRepository;
import br.com.passos.api_reserva_de_mesas.domain.mesa.Status;
import br.com.passos.api_reserva_de_mesas.service.exception.IdentificadorJaCadastradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;

    public MesaService(MesaRepository mesaRepository) {
        this.mesaRepository = mesaRepository;
    }

    @Transactional
    public Mesa cadastrar(Mesa mesa) {
        validarIdentificador(mesa.getIdentificador());
        return mesaRepository.save(mesa);
    }

    @Transactional
    public void atualizarStatusMesa(long id) {
        mesaRepository.atualizarStatus(id, Status.RESERVADO);
    }

    private void validarIdentificador(String identificador) {
        if (mesaRepository.existsByIdentificador(identificador)) {
            throw new IdentificadorJaCadastradoException("Identificador da mesa cadastrado");
        }
    }

}
