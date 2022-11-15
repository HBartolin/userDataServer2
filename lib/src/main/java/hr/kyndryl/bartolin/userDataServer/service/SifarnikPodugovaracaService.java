package hr.kyndryl.bartolin.userDataServer.service;

import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public interface SifarnikPodugovaracaService {
	PojoInterface urediPodugovarace();
	PojoInterface editirajSifarnikPodugovaraca(Optional<Long> id, Optional<String> naziv);
}
