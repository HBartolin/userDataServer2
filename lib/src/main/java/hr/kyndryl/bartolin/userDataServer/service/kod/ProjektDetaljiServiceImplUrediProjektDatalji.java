package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.db.Projekt;
import hr.kyndryl.bartolin.userDataServer.db.ProjektDetalji;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikValuta;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public class ProjektDetaljiServiceImplUrediProjektDatalji extends Kod {
	private Long id=(Long) hm.get("id");
	private String totalRevenue=(String) hm.get("totalRevenue");
	private String costPs=(String) hm.get("costPs");
	private String greska="";

	public ProjektDetaljiServiceImplUrediProjektDatalji(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		greska="";
		BigDecimal totalRevenueBD=getBigDecimalValue(totalRevenue, "Total revenue");			
		BigDecimal costPsBD=getBigDecimalValue(costPs, "Cost");
		
		if(greska!="") {
			pi.setGreska(greska);
		} else {
			pi.setRezultat(ucitajProjektDetalje(id, totalRevenueBD, costPsBD));
		}
		
		return pi;
	}
	
	private ProjektDetalji ucitajProjektDetalje(Long id, BigDecimal totalRevenueBD, BigDecimal costPsBD) {
		Optional<ProjektDetalji> projektDetaljiOptional=kodRepository.getProjektDetaljiRepository().findById(id);
		ProjektDetalji projektDetalji;
		
		if(projektDetaljiOptional.isPresent()) {
			projektDetalji=projektDetaljiOptional.get();
		} else {
			Optional<SifarnikValuta> sifarnikValuta=kodRepository.getSifarnikValutaRepository().findAllByNaziv(HRK);
			Optional<Projekt> projektO=kodRepository.getProjektRepository().findById(id);
			
			projektDetalji=new ProjektDetalji();
			projektDetalji.setSifarnikValuta(sifarnikValuta.get());
			projektDetalji.setCostPlanned(BigDecimal.ZERO);
			projektDetalji.setCostActual(BigDecimal.ZERO);
			projektDetalji.setProjekt(projektO.get());
		}
		
		projektDetalji.setTotalRevenue(totalRevenueBD);
		projektDetalji.setCostPs(costPsBD);
		
		projektDetalji=kodRepository.getProjektDetaljiRepository().save(projektDetalji);
		
		return projektDetalji;
	}

	
	private BigDecimal getBigDecimalValue(String value, String text) {
		try {
			BigDecimal valueBD=new BigDecimal(value);
			
			if(!(valueBD.compareTo(BigDecimal.ZERO)>0)) {
				if(greska.length()>0) greska+=" <BR> ";
				greska+=String.format("Iznosi za '%s' moraju biti veći od nule.", text);
			}
			
			return valueBD;
		} catch(NumberFormatException e) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="Iznosi za '%d' moraju biti brojke.";
			
			return BigDecimal.ONE;
		}
	}

}
