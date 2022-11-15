package hr.bart.userDataServer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.ClaimPodugovarac;
	
public interface ClaimPodugovaracRepository extends CrudRepository<ClaimPodugovarac, Long> {
	@Query("SELECT po FROM ClaimPodugovarac po WHERE po.projektDetalji.id=:idProjektDetalji ORDER BY po.sifarnikPodugovaraca.naziv")
	Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji(@Param("idProjektDetalji") Long idProjektDetalji);
	
	@Query("SELECT po FROM ClaimPodugovarac po WHERE po.projektDetalji.id=:idProjektDetalji AND po.sifarnikPodugovaraca.id=:idSifarnikPodugovaraca")
	Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_idSifarnikPodugovaraca(@Param("idProjektDetalji") Long idProjektDetalji, @Param("idSifarnikPodugovaraca") Long idSifarnikPodugovaraca);
	
	@Query("SELECT po FROM ClaimPodugovarac po WHERE po.projektDetalji.id=:idProjektDetalji AND po.id<>:id AND po.sifarnikPodugovaraca.id=:idSifarnikPodugovaraca")
	Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_id_idSifarnikPodugovaraca(@Param("idProjektDetalji") Long idProjektDetalji, @Param("id") Long id, @Param("idSifarnikPodugovaraca") Long idSifarnikPodugovaraca);
}
