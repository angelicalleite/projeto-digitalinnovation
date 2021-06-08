package bio.especies.ameaca.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nivel {

    CR("Criticamente em Perigo"),
    EN("Em perigo"),
    VU("Vulnerável"),
    NT("Quase ameaçada"),
    LC("Menos preocupante"),
    DD("Dados insuficientes"),
    EX("Extinta"),
    EW("Extinta na natureza");

    private final String descricao;
}
