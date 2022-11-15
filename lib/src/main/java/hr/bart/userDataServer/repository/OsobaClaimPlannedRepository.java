package hr.bart.userDataServer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.OsobaClaimPlanned;

public interface OsobaClaimPlannedRepository extends CrudRepository<OsobaClaimPlanned, Long> {
	@Query("SELECT ocp FROM OsobaClaimPlanned ocp WHERE ocp.claim.projektDetalji.id=:idProjektDetalji AND ocp.sifarnikMjeseca.mjesec=:datum")
	Optional<List<OsobaClaimPlanned>> findAllByDatum(@Param("idProjektDetalji") Long idProjektDetalji, @Param("datum") LocalDate datum);
	
	@Query("SELECT ocp FROM OsobaClaimPlanned ocp WHERE ocp.claim.id=:idClaim ORDER BY ocp.sati")
	Optional<List<OsobaClaimPlanned>> findAllByIdClaim(@Param("idClaim") Long idClaim);
}
