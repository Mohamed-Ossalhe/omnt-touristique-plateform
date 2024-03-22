package bourra.regionsservice.repositories;

import bourra.regionsservice.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CountryRepository extends JpaRepository<Country, UUID> {
}
