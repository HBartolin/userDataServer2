package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringExclude;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.db.SifarnikValuta;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.ProjektRepository;
import hr.bart.userDataServer.repository.SifarnikValutaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplUrediProjektDatalji extends Kod {
	private final Long id;
	private final String totalRevenue;
	private final String costPs;
	@ToStringExclude
	private final ProjektDetaljiRepository projektDetaljiRepository;
	@ToStringExclude
	private final SifarnikValutaRepository sifarnikValutaRepository;
	@ToStringExclude
	private final ProjektRepository projektRepository;

	public ProjektDetaljiServiceImplUrediProjektDatalji(
			ProjektDetaljiRepository projektDetaljiRepository, 
			SifarnikValutaRepository sifarnikValutaRepository,
			ProjektRepository projektRepository,
			Long id, 
			String totalRevenue, 
			String costPs) {
		this.projektDetaljiRepository=projektDetaljiRepository;
		this.sifarnikValutaRepository=sifarnikValutaRepository;
		this.projektRepository=projektRepository;
		this.id=id;
		this.totalRevenue=totalRevenue;
		this.costPs=costPs;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Map<String, Object> map) throws Throwable {
		BigDecimal totalRevenueBD=getBigDecimalValue(pi, totalRevenue, "Total revenue");			
		BigDecimal costPsBD=getBigDecimalValue(pi, costPs, "Cost");
		
		if(pi.getGreska().isEmpty()) {
			pi.setRezultat(ucitajProjektDetalje(id, totalRevenueBD, costPsBD));
		}
		
		return pi;
	}
	
	private ProjektDetalji ucitajProjektDetalje(Long id, BigDecimal totalRevenueBD, BigDecimal costPsBD) {
		Optional<ProjektDetalji> projektDetaljiOptional=projektDetaljiRepository.findById(id);
		ProjektDetalji projektDetalji;
		
		if(projektDetaljiOptional.isPresent()) {
			projektDetalji=projektDetaljiOptional.get();
		} else {
			Optional<SifarnikValuta> sifarnikValuta=sifarnikValutaRepository.findAllByNaziv(HRK);
			Optional<Projekt> projektO=projektRepository.findById(id);
			
			projektDetalji=new ProjektDetalji();
			projektDetalji.setSifarnikValuta(sifarnikValuta.get());
			projektDetalji.setCostPlanned(BigDecimal.ZERO);
			projektDetalji.setCostActual(BigDecimal.ZERO);
			projektDetalji.setProjekt(projektO.get());
		}
		
		projektDetalji.setTotalRevenue(totalRevenueBD);
		projektDetalji.setCostPs(costPsBD);
		
		projektDetalji=projektDetaljiRepository.save(projektDetalji);
		
		return projektDetalji;
	}

	
	private BigDecimal getBigDecimalValue(PojoInterface pi, String value, String text) {
		try {
			BigDecimal valueBD=new BigDecimal(value);
			
			if(!(valueBD.compareTo(BigDecimal.ZERO)>0)) {
				String msg=String.format("Iznosi za '%s' moraju biti veći od nule.", text);
				pi.addGreskaList(msg);
			}
			
			return valueBD;
		} catch(NumberFormatException e) {
			String msg=String.format("Iznosi za '%s' moraju biti brojke.", text);
			pi.addGreskaList(msg);
			
			return BigDecimal.ONE;
		}
	}
	
}
