package bio.especies.ameaca.repositories;

import bio.especies.ameaca.entities.Ameaca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmeacaRepository extends JpaRepository<Ameaca, Long> {
}
