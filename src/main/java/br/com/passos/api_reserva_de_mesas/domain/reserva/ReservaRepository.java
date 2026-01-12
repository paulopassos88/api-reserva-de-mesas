package br.com.passos.api_reserva_de_mesas.domain.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
                SELECT COUNT(r) > 0
                FROM Reserva r
                WHERE r.dataReserva = :dataReserva
            """)
    boolean existeReservaNaData(@Param("dataReserva") LocalDateTime dataReserva);

}
