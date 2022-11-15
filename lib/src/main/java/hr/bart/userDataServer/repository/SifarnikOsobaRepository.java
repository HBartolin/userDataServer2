package hr.bart.userDataServer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import hr.bart.userDataServer.db.SifarnikOsoba;

public interface SifarnikOsobaRepository extends CrudRepository<SifarnikOsoba, Long> {
	@Query("SELECT so FROM SifarnikOsoba so ORDER BY so.prezime, so.ime")
	Page<List<SifarnikOsoba>> findAll(Pageable pageRequest);
}
