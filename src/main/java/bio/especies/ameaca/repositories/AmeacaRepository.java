package bio.especies.ameaca.repositories;

import bio.especies.ameaca.entities.Ameaca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AmeacaRepository extends JpaRepository<Ameaca, Long> {
    Optional<Ameaca> findByEspecie(String name);
}
