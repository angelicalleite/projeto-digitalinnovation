package bio.especies.ameaca.services;

import bio.especies.ameaca.dto.mapper.AmeacaMapper;
import bio.especies.ameaca.dto.request.AmeacaDTO;
import bio.especies.ameaca.dto.response.MessageResponseDTO;
import bio.especies.ameaca.entities.Ameaca;
import bio.especies.ameaca.exception.AmeacaNotFoundException;
import bio.especies.ameaca.repositories.AmeacaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AmeacaService {

    private final AmeacaRepository ameacaRepository;

    private final AmeacaMapper ameacaMapper;

    public MessageResponseDTO create(AmeacaDTO ameacaDTO) {
        Ameaca ameaca = ameacaMapper.toModel(ameacaDTO);
        Ameaca savedAmeaca = ameacaRepository.save(ameaca);

        MessageResponseDTO messageResponse = createMessageResponse("Ameaca successfully created with ID ", savedAmeaca.getId());

        return messageResponse;
    }

    public AmeacaDTO findById(Long id) throws AmeacaNotFoundException {
        Ameaca ameaca = ameacaRepository.findById(id)
                .orElseThrow(() -> new AmeacaNotFoundException(id));

        return ameacaMapper.toDTO(ameaca);
    }

    public List<AmeacaDTO> listAll() {
        List<Ameaca> people = ameacaRepository.findAll();
        return people.stream()
                .map(ameacaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MessageResponseDTO update(Long id, AmeacaDTO ameacaDTO) throws AmeacaNotFoundException {
        ameacaRepository.findById(id)
                .orElseThrow(() -> new AmeacaNotFoundException(id));

        Ameaca updatedAmeaca = ameacaMapper.toModel(ameacaDTO);
        Ameaca savedAmeaca = ameacaRepository.save(updatedAmeaca);

        MessageResponseDTO messageResponse = createMessageResponse("Ameaca successfully updated with ID ", savedAmeaca.getId());

        return messageResponse;
    }

    public void delete(Long id) throws AmeacaNotFoundException {
        ameacaRepository.findById(id)
                .orElseThrow(() -> new AmeacaNotFoundException(id));

        ameacaRepository.deleteById(id);
    }

    private MessageResponseDTO createMessageResponse(String s, Long id2) {
        return MessageResponseDTO.builder()
                .message(s + id2)
                .build();
    }
}
