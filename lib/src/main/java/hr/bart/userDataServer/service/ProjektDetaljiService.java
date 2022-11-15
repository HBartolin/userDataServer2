package hr.bart.userDataServer.service;

import hr.bart.userDataServer.util.PojoInterface;

public interface ProjektDetaljiService {
	PojoInterface projektDatalji(Long id);
	PojoInterface urediProjektDatalji(Long id, String totalRevenue, String costPs);
}
