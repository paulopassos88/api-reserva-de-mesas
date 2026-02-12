package br.com.passos.api_reserva_de_mesas.domain.reserva;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query("""
                SELECT COUNT(r) > 0
                FROM Reserva r
                WHERE r.dataReserva = :dataReserva
                and r.mesa.id = :idMesa
                and r.ativa = true
            """)
    boolean existeReservaNaData(@Param("dataReserva") LocalDateTime dataReserva, @Param("idMesa") long idMesa);

    @Query("""
        select r
        from Reserva r
        where r.id = :idReserva
              and r.usuario.id = :usuarioId
              and r.ativa = true
        """)
    Optional<Reserva> buscarReservaPorIdEUsuario(
            @Param("idReserva") Long idReserva,
            @Param("usuarioId") Long usuarioId
    );

    @Override
    Page<Reserva> findAll(Pageable pageable);
}
