package hr.bart.userDataServer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.SifarnikDatuma;

public interface SifarnikDatumaRepository extends CrudRepository<SifarnikDatuma, Long> {
	@Query("SELECT sd FROM SifarnikDatuma sd WHERE sd.datumPetak=:datumPetak ORDER BY sd.id")
	List<SifarnikDatuma> findByDatumPetak(@Param("datumPetak") LocalDate datumPetak);
	
}
