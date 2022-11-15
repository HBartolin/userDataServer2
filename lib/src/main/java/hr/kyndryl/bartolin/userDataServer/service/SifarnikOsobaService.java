package hr.kyndryl.bartolin.userDataServer.service;

import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public interface SifarnikOsobaService {
	PojoInterface sifarniciOsoba();
	PojoInterface editirajSifarnikOsoba(Optional<Long> id, String ime, String prezime);
	PojoInterface tablicaSifarnikOsoba(int pageNumber);
}
