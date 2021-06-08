package bio.especies.ameaca.dto.request;

import bio.especies.ameaca.enums.Nivel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;

    @Enumerated(EnumType.STRING)
    private Nivel nivel;

    @NotEmpty
    @Size
    private String descricao;
}
