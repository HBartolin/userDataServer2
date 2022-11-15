package hr.kyndryl.bartolin.userDataServer.service;

import java.math.BigDecimal;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public interface ClaimPodugovaracService {
	PojoInterface purchaseOrders(Long idProjektDetalji);
	PojoInterface unesiPO(Optional<Long> idO, Optional<Long> tsO, Long idProjektDetalji, Long idSifarnikPodugovaraca, String po, Optional<BigDecimal> total);
}
