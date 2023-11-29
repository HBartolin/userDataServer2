package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.util.Optional;

import hr.bart.userDataServer.db.Projekt;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.db.SifarnikValuta;
import hr.bart.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplUrediProjektDatalji extends Kod {
	private final Long id;
	private final String totalRevenue;
	private final String costPs;

	public ProjektDetaljiServiceImplUrediProjektDatalji(KodRepository kodRepository, Long id, String totalRevenue, String costPs) {
		super(kodRepository);
		this.id=id;
		this.totalRevenue=totalRevenue;
		this.costPs=costPs;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		BigDecimal totalRevenueBD=getBigDecimalValue(pi, totalRevenue, "Total revenue");			
		BigDecimal costPsBD=getBigDecimalValue(pi, costPs, "Cost");
		
		if(!pi.getGreska().isEmpty()) {
			
		} else {
			pi.setRezultat(ucitajProjektDetalje(id, totalRevenueBD, costPsBD));
		}
		
		return pi;
	}
	
	private ProjektDetalji ucitajProjektDetalje(Long id, BigDecimal totalRevenueBD, BigDecimal costPsBD) {
		Optional<ProjektDetalji> projektDetaljiOptional=getKodRepository().getProjektDetaljiRepository().findById(id);
		ProjektDetalji projektDetalji;
		
		if(projektDetaljiOptional.isPresent()) {
			projektDetalji=projektDetaljiOptional.get();
		} else {
			Optional<SifarnikValuta> sifarnikValuta=getKodRepository().getSifarnikValutaRepository().findAllByNaziv(HRK);
			Optional<Projekt> projektO=getKodRepository().getProjektRepository().findById(id);
			
			projektDetalji=new ProjektDetalji();
			projektDetalji.setSifarnikValuta(sifarnikValuta.get());
			projektDetalji.setCostPlanned(BigDecimal.ZERO);
			projektDetalji.setCostActual(BigDecimal.ZERO);
			projektDetalji.setProjekt(projektO.get());
		}
		
		projektDetalji.setTotalRevenue(totalRevenueBD);
		projektDetalji.setCostPs(costPsBD);
		
		projektDetalji=getKodRepository().getProjektDetaljiRepository().save(projektDetalji);
		
		return projektDetalji;
	}

	
	private BigDecimal getBigDecimalValue(PojoInterface pi, String value, String text) {
		try {
			BigDecimal valueBD=new BigDecimal(value);
			
			if(!(valueBD.compareTo(BigDecimal.ZERO)>0)) {
				String msg=String.format("Iznosi za '%s' moraju biti veći od nule.", text);
				pi.setGreskaListString(msg);
			}
			
			return valueBD;
		} catch(NumberFormatException e) {
			String msg=String.format("Iznosi za '%s' moraju biti brojke.", text);
			pi.setGreskaListString(msg);
			
			return BigDecimal.ONE;
		}
	}

}
