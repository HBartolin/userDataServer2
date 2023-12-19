package hr.bart.userDataServer.repository;

import org.springframework.data.repository.CrudRepository;

import hr.bart.userDataServer.db.ProjektDetalji;

public interface ProjektDetaljiRepository extends CrudRepository<ProjektDetalji, Long> {
	
//	@Query("SELECT pd FROM ProjektDetalji pd, Projekt p WHERE p.id=:id AND pd.id=p.id")
//	Optional<ProjektDetalji> findById(@Param("id") Long id);
}
