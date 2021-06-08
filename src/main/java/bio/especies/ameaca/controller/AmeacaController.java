package bio.especies.ameaca.controller;

import bio.especies.ameaca.dto.request.AmeacaDTO;
import bio.especies.ameaca.dto.response.MessageResponseDTO;
import bio.especies.ameaca.exception.AmeacaNotFoundException;
import bio.especies.ameaca.services.AmeacaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/people")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AmeacaController {

    private final AmeacaService ameacaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO create(@RequestBody @Valid AmeacaDTO ameacaDTO) {
        return ameacaService.create(ameacaDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AmeacaDTO findById(@PathVariable Long id) throws AmeacaNotFoundException {
        return ameacaService.findById(id);
    }

    @GetMapping
    public List<AmeacaDTO> listAll() {
        return ameacaService.listAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponseDTO update(@PathVariable Long id, @RequestBody @Valid AmeacaDTO ameacaDTO) throws AmeacaNotFoundException {
        return ameacaService.update(id, ameacaDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws AmeacaNotFoundException {
        ameacaService.delete(id);
    }
}
