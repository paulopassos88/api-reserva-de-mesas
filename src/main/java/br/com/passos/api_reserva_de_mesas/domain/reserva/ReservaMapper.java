package br.com.passos.api_reserva_de_mesas.domain.reserva;

import br.com.passos.api_reserva_de_mesas.domain.mesa.MesaMapper;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        uses = {UsuarioMapper.class, MesaMapper.class}
)
public interface ReservaMapper {

    ReservaMapper INSTANCE = Mappers.getMapper(ReservaMapper.class);

    @Mapping(target = "id", ignore = true)
    Reserva toModel(ReservaRequest reservaRequest);

    ReservaResponse toResponseDTO(Reserva reserva);
}
