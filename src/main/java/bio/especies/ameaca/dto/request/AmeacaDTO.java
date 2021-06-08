package bio.especies.ameaca.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AmeacaDTO {

    private Long id;

    @NotEmpty
    private String especie;

    @NotEmpty
    private String ano;

    @NotEmpty
    private String local;

    @NotNull
    private String data;

    @Valid
    @NotEmpty
    private List<CategoriaDTO> categorias;
}
