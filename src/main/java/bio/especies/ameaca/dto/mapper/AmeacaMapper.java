package bio.especies.ameaca.dto.mapper;

import bio.especies.ameaca.dto.request.AmeacaDTO;
import bio.especies.ameaca.entities.Ameaca;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AmeacaMapper {

    AmeacaMapper INSTANCE = Mappers.getMapper(AmeacaMapper.class);

    @Mapping(target = "data", source = "data", dateFormat = "dd-MM-yyyy")
    Ameaca toModel(AmeacaDTO dto);

    AmeacaDTO toDTO(Ameaca dto);
}
