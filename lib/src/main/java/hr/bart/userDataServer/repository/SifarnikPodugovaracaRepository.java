package hr.bart.userDataServer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import hr.bart.userDataServer.db.SifarnikPodugovaraca;

public interface SifarnikPodugovaracaRepository extends CrudRepository<SifarnikPodugovaraca, Long> {
	@Query("SELECT sp FROM SifarnikPodugovaraca sp ORDER BY sp.naziv")
	List<SifarnikPodugovaraca> findAll();
}
