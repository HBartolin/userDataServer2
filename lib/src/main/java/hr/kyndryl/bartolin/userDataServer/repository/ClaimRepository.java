package hr.kyndryl.bartolin.userDataServer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.kyndryl.bartolin.userDataServer.db.Claim;

public interface ClaimRepository extends CrudRepository<Claim, Long> {
	@Query("SELECT c FROM Claim c WHERE c.projektDetalji.id=:idProjektDetalji ORDER BY c.sifarnikOsoba.prezime")
	Optional<List<Claim>> findAllByIdProjektDetalji(@Param("idProjektDetalji") Long idProjektDetalji);
	
	@Query("SELECT c FROM Claim c WHERE c.projektDetalji.id=:idProjektDetalji AND c.sifarnikOsoba.id=:idSifarnikOsoba")
	Optional<List<Claim>> findAllByIdProjektDetalji_idSifarnikOsoba(@Param("idProjektDetalji") Long idProjektDetalji, @Param("idSifarnikOsoba") Long idSifarnikOsoba);
}
