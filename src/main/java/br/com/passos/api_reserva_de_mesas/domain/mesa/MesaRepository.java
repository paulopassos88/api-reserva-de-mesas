package br.com.passos.api_reserva_de_mesas.domain.mesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    boolean existsByIdentificador(String identificador);

    @Query("SELECT m.status FROM Mesa m WHERE m.id = :id")
    Optional<Status> statusMesa(Long id);

}
