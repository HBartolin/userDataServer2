package hr.bart.userDataServer.service;

import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;

public interface SifarnikPodugovaracaService {
	PojoInterface urediPodugovarace();
	PojoInterface editirajSifarnikPodugovaraca(Optional<Long> id, Optional<String> naziv);
}
