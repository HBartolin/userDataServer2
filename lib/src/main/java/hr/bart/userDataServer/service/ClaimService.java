package hr.bart.userDataServer.service;

import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;

public interface ClaimService {
	PojoInterface tablicaOsobaValuta(Long idProjektDetalji);
	PojoInterface novaOsoba();
	PojoInterface claimImena(Optional<List<Long>> podatci);
}
