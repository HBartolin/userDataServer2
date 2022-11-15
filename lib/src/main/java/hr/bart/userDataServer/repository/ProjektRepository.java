package hr.bart.userDataServer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.util.DbStatus;

public interface ProjektRepository extends CrudRepository<Projekt, Long> {
	
	@Query("SELECT p FROM Projekt p WHERE p.status = :status ORDER BY p.id")
	Page<List<Projekt>> findByStatus(@Param("status") DbStatus status, Pageable pageRequest);
	
	@Query("SELECT p FROM Projekt p ORDER BY p.id")
	Page<List<Projekt>> findAll(Pageable pageRequest);
	
	@Query("SELECT p FROM Projekt p WHERE UPPER(p.claim) LIKE :trazi OR UPPER(p.contract) LIKE :trazi ORDER BY p.id")
	Page<List<Projekt>> likeClaimContract(@Param("trazi") String trazi, Pageable pageRequest);
}
