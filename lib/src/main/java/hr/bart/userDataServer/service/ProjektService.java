package hr.bart.userDataServer.service;

import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;
import hr.bart.userDataServer.util.ZatvoriOtvori;

public interface ProjektService {
	PojoInterface projekti(Optional<String> status);
	PojoInterface zatvoriOtvoriProjekt(ZatvoriOtvori zo, Long id, Long ts, Optional<String> status);
	PojoInterface noviProjekt(String claim, String contract);
	PojoInterface tablicaProjekti(int pageNumber, Optional<String> status);
	PojoInterface traziProjekt(String trazi);
}
