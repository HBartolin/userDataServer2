package hr.bart.userDataServer.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;

public interface PodugovaracService {
	PojoInterface podugovaraci(Long idProjektDetalji);
	PojoInterface unesiPodugovarac(Optional<Long> id, Long ts, Long idProjektDetalji, Optional<Long> idPurchaseOrder, LocalDate datumPlanned, LocalDate datumActual, Optional<BigDecimal> cijena, Optional<Long> invoiceNumber);
}
