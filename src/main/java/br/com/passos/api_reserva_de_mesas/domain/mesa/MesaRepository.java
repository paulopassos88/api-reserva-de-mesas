package br.com.passos.api_reserva_de_mesas.domain.mesa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MesaRepository extends JpaRepository<Mesa, Long> {

    boolean existsByIdentificador(String identificador);

    @Query("SELECT m.status FROM Mesa m WHERE m.id = :id")
    Status statusMesa(Long id);

    @Query("SELECT m.capacidade FROM Mesa m WHERE m.id = :id")
    Integer capacidadeMesa(Long id);

    boolean existsMesaBy(long id);

    @Modifying
    @Query("UPDATE Mesa m SET m.status = :status WHERE m.id = :id")
    void atualizarStatus(@Param("id") Long id, @Param("status") Status status);
}
