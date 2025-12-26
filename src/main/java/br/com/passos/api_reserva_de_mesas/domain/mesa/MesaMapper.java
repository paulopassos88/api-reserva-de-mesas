package br.com.passos.api_reserva_de_mesas.domain.mesa;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MesaMapper {

    MesaMapper INSTANCE = Mappers.getMapper(MesaMapper.class);

    @Mapping(target = "id", ignore = true)
    Mesa toModel(MesaRequest mesaRequest);

    MesaResponse toResponseDTO(Mesa mesa);
}
