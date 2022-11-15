package hr.kyndryl.bartolin.userDataServer.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import hr.kyndryl.bartolin.userDataServer.util.ClaimUpdatedActualPlanned;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public interface OsobaClaimActualService {
	PojoInterface claimNewActualByDate(Long idProjektDetalji, LocalDate datum, HashMap<String, String> podatci);
	PojoInterface claimUpdatedActualByDate(Long idProjektDetalji, LocalDate datum, List<ClaimUpdatedActualPlanned> podatci);
}
