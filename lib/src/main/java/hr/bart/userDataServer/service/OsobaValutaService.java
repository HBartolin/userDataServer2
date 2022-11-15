package hr.bart.userDataServer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;

public interface OsobaValutaService {
	PojoInterface osobaValuta(Long idSifarnikOsoba);
	PojoInterface unesiOsobaValuta(Optional<Long> id, Long ts, Long idSifarnikOsoba, String band, BigDecimal cijena, LocalDate sifarnikDatumaOd, LocalDate sifarnikDatumaDo);
	PojoInterface tablicaOsobaValuta(int pageNumber, Long idSifarnikOsoba);
}
