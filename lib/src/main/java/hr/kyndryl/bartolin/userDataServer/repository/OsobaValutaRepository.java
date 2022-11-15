package hr.kyndryl.bartolin.userDataServer.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import hr.kyndryl.bartolin.userDataServer.db.OsobaValuta;

public interface OsobaValutaRepository extends CrudRepository<OsobaValuta, Long> {
	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.sifarnikOsoba.id=:idSifarnikOsoba ORDER BY ov.sifarnikDatumaOd.datumPetak")
	Page<List<OsobaValuta>> findAllBySifarnikOsobaId(@Param("idSifarnikOsoba") long idSifarnikOsoba, Pageable pageRequest);
	
	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.sifarnikOsoba.id=:idSifarnikOsoba ORDER BY ov.sifarnikDatumaOd.datumPetak")
	List<OsobaValuta> findAllBySifarnikOsobaId(@Param("idSifarnikOsoba") long idSifarnikOsoba);
	
	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.id<>:id AND ov.sifarnikOsoba.id=:idSifarnikOsoba AND ov.sifarnikDatumaOd.datumPetak <= :sifarnikDatumaOd AND ov.sifarnikDatumaDo.datumPetak >= :sifarnikDatumaOd ORDER BY ov.sifarnikDatumaOd.datumPetak")
	List<OsobaValuta> findAllByIdSifarnikDatumaOd(@Param("id") long id, @Param("idSifarnikOsoba") long idSifarnikOsoba, @Param("sifarnikDatumaOd") LocalDate sifarnikDatumaOd);
	
	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.sifarnikOsoba.id=:idSifarnikOsoba AND ov.sifarnikDatumaOd.datumPetak <= :sifarnikDatumaOd AND ov.sifarnikDatumaDo.datumPetak >= :sifarnikDatumaOd ORDER BY ov.sifarnikDatumaOd.datumPetak")
	List<OsobaValuta> findAllBySifarnikDatumaOd(@Param("idSifarnikOsoba") long idSifarnikOsoba, @Param("sifarnikDatumaOd") LocalDate sifarnikDatumaOd);

	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.id<>:id AND ov.sifarnikOsoba.id=:idSifarnikOsoba AND ov.sifarnikDatumaOd.datumPetak <= :sifarnikDatumaDo AND ov.sifarnikDatumaDo.datumPetak >= :sifarnikDatumaDo ORDER BY ov.sifarnikDatumaOd.datumPetak")
	List<OsobaValuta> findAllByIdSifarnikDatumaDo(@Param("id") long id, @Param("idSifarnikOsoba") long idSifarnikOsoba, @Param("sifarnikDatumaDo") LocalDate sifarnikDatumaDo);
	
	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.sifarnikOsoba.id=:idSifarnikOsoba AND ov.sifarnikDatumaOd.datumPetak <= :sifarnikDatumaDo AND ov.sifarnikDatumaDo.datumPetak >= :sifarnikDatumaDo ORDER BY ov.sifarnikDatumaOd.datumPetak")
	List<OsobaValuta> findAllBySifarnikDatumaDo(@Param("idSifarnikOsoba") long idSifarnikOsoba, @Param("sifarnikDatumaDo") LocalDate sifarnikDatumaDo);
	
	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.sifarnikOsoba.id=:idSifarnikOsoba AND ov.sifarnikDatumaOd.datumPetak <= :datum AND ov.sifarnikDatumaDo.datumPetak >= :datum")
	List<OsobaValuta> findAllBySifarnikOsobaId_datum(@Param("idSifarnikOsoba") long idSifarnikOsoba, @Param("datum") LocalDate datum);

	@Query("SELECT ov FROM OsobaValuta ov WHERE ov.sifarnikOsoba.id IN (:idSifarnikOsobaList) ORDER BY ov.sifarnikOsoba.prezime, ov.sifarnikDatumaOd.datumPetak")
	Optional<List<OsobaValuta>> findAllByIdSifarnikOsobaList(@Param("idSifarnikOsobaList") List<Long> idSifarnikOsobaList);
}
