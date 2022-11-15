package hr.bart.userDataServer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.Podugovarac;

public interface PodugovaracRepository extends CrudRepository<Podugovarac, Long> {
	@Query("SELECT p FROM Podugovarac p WHERE p.claimPodugovarac.projektDetalji.id=:idProjektDetalji ORDER BY p.datumPlanned, p.datumActual, UPPER(p.claimPodugovarac.sifarnikPodugovaraca.naziv)")
	Optional<List<Podugovarac>> findAllByIdProjektDetalji(@Param("idProjektDetalji") Long idProjektDetalji);
	
	@Query("SELECT p FROM Podugovarac p WHERE p.claimPodugovarac.projektDetalji.id=:idProjektDetalji AND p.datumActual IS NOT NULL ORDER BY p.datumPlanned, p.datumActual, UPPER(p.claimPodugovarac.sifarnikPodugovaraca.naziv)")
	Optional<List<Podugovarac>> findAllByIdProjektDetalji_datumActual(@Param("idProjektDetalji") Long idProjektDetalji);
	
	@Query("SELECT p FROM Podugovarac p WHERE p.claimPodugovarac.id=:idClaimPodugovarac")
	Optional<List<Podugovarac>> findAllByIdClaimPodugovarac(@Param("idClaimPodugovarac") Long idClaimPodugovarac);
}
