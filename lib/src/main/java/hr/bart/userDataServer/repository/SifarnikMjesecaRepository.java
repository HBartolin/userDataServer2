package hr.bart.userDataServer.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.SifarnikMjeseca;

public interface SifarnikMjesecaRepository extends CrudRepository<SifarnikMjeseca, Long>{
	@Query("SELECT sm FROM SifarnikMjeseca sm WHERE sm.mjesec=:mjesec ORDER BY sm.id")
	List<SifarnikMjeseca> findByMjeseca(@Param("mjesec") LocalDate mjesec);
}
