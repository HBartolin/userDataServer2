package hr.bart.userDataServer.service;

import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;

public interface SifarnikOsobaService {
	PojoInterface sifarniciOsoba();
	PojoInterface editirajSifarnikOsoba(Optional<Long> id, String ime, String prezime);
	PojoInterface tablicaSifarnikOsoba(int pageNumber);
}
