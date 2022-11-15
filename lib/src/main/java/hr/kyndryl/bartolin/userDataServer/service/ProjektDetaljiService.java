package hr.kyndryl.bartolin.userDataServer.service;

import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public interface ProjektDetaljiService {
	PojoInterface projektDatalji(Long id);
	PojoInterface urediProjektDatalji(Long id, String totalRevenue, String costPs);
}
