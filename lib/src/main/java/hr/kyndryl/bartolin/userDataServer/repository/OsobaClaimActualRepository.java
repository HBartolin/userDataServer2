package hr.kyndryl.bartolin.userDataServer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.kyndryl.bartolin.userDataServer.db.OsobaClaimActual;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikOsoba;

public interface OsobaClaimActualRepository extends CrudRepository<OsobaClaimActual, Long> {
//	@Query("SELECT oca FROM OsobaClaimActual oca WHERE oca.claimId=:idClaim ORDER BY oca.sifarnikDatuma.datumPetak")
//	Optional<List<OsobaClaimActual>> findAllByIdClaim(@Param("idClaim") Long idClaim);
	
	@Query("SELECT oca FROM OsobaClaimActual oca, Claim c WHERE c.projektDetalji.id=:idProjektDetalji AND oca.sifarnikDatuma.datumPetak=:datum")
	Optional<List<OsobaClaimActual>> findAllByDatum(@Param("idProjektDetalji") Long idProjektDetalji, @Param("datum") LocalDate datum);
	
	@Query("SELECT oca FROM OsobaClaimActual oca WHERE oca.claim.id=:idClaim ORDER BY oca.sati")
	Optional<List<OsobaClaimActual>> findAllByIdClaim(@Param("idClaim") Long idClaim);
	
	@Query("SELECT oca FROM OsobaClaimActual oca, Claim c WHERE c.sifarnikOsoba=:sifarnikOsoba AND oca.sifarnikDatuma.datumPetak>=:datumOd AND oca.sifarnikDatuma.datumPetak<=:datumDo")
	Optional<List<OsobaClaimActual>> findAllBySifarnikOsoba_datumi(@Param("sifarnikOsoba") SifarnikOsoba sifarnikOsoba, @Param("datumOd") LocalDate datumOd, @Param("datumDo") LocalDate datumDo);
}
