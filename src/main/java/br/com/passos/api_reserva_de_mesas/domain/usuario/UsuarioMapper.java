package br.com.passos.api_reserva_de_mesas.domain.usuario;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    @Mapping(target = "id", ignore = true)
    Usuario toModel(UsuarioRequest usuarioRequest);

    UsuarioResponse toResponseDTO(Usuario usuario);
}
