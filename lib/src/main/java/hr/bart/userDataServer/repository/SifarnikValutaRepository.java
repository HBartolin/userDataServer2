package hr.bart.userDataServer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.SifarnikValuta;

public interface SifarnikValutaRepository extends CrudRepository<SifarnikValuta, Long> {
	@Query("SELECT sv FROM SifarnikValuta sv WHERE sv.naziv=:naziv")
	Optional<SifarnikValuta> findAllByNaziv(@Param("naziv") String naziv);
}
