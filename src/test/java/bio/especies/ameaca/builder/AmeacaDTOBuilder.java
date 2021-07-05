package bio.especies.ameaca.builder;

import bio.especies.ameaca.dto.request.AmeacaDTO;
import bio.especies.ameaca.entities.Categoria;
import bio.especies.ameaca.enums.Nivel;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Builder
public class AmeacaDTOBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String especie = "Lobo Guará";

    @Builder.Default
    private String ano = "2020";

    @Builder.Default
    private String local = "DF";

    @Builder.Default
    private LocalDate data = LocalDate.now();

    @Builder.Default
    private List<Categoria> categorias = Arrays.asList(new Categoria(1L, Nivel.CR, "Criticamente em Perigo"));

    public AmeacaDTO toAmeacaDTO() {
        return new AmeacaDTO(id,
                especie,
                ano,
                local,
                data.toString(),
                null);    }

}
