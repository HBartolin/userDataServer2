package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import hr.kyndryl.bartolin.userDataServer.db.Claim;
import hr.kyndryl.bartolin.userDataServer.db.ClaimPodugovarac;
import hr.kyndryl.bartolin.userDataServer.db.OsobaClaimActual;
import hr.kyndryl.bartolin.userDataServer.db.OsobaClaimPlanned;
import hr.kyndryl.bartolin.userDataServer.db.OsobaValuta;
import hr.kyndryl.bartolin.userDataServer.db.Projekt;
import hr.kyndryl.bartolin.userDataServer.db.ProjektDetalji;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikDatuma;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikMjeseca;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikOsoba;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikPodugovaraca;
import hr.kyndryl.bartolin.userDataServer.db.SifarnikValuta;
import hr.kyndryl.bartolin.userDataServer.util.DbStatus;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

public class RestServiceImplCreateDB extends Kod {
	
	public RestServiceImplCreateDB(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		SifarnikValuta sv=new SifarnikValuta();
		sv.setNaziv("HRK");
		sv=kodRepository.getSifarnikValutaRepository().save(sv);
		pi.setOk("SifarnikValuta");
		
		SifarnikOsoba so=new SifarnikOsoba();
		so.setIme("Ime");
		so.setPrezime("Prezime");
		so=kodRepository.getSifarnikOsobaRepository().save(so);
		pi.setOk(pi.getOk() + ", SifarnikOsoba");
		
		SifarnikMjeseca smDo=new SifarnikMjeseca();
		smDo.setMjesec(LocalDate.of(9999, 12, 1));
		smDo=kodRepository.getSifarnikMjesecaRepository().save(smDo);
		pi.setOk(pi.getOk() + ", SifarnikMjeseca");
		
		SifarnikDatuma sdDo=new SifarnikDatuma();
		sdDo.setDatumNedjelja(LocalDate.of(9999, 12, 26));
		sdDo.setDatumPetak(LocalDate.of(9999, 12, 24));
		sdDo.setMjesec(smDo);
		sdDo=kodRepository.getSifarnikDatumaRepository().save(sdDo);
		pi.setOk(pi.getOk() + ", SifarnikDatuma");
		
		SifarnikMjeseca smOd=new SifarnikMjeseca();
		smOd.setMjesec(LocalDate.of(2000, 1, 1));
		smOd=kodRepository.getSifarnikMjesecaRepository().save(smOd);
		pi.setOk(pi.getOk() + ", SifarnikMjeseca");
		
		SifarnikDatuma sdOd=new SifarnikDatuma();
		sdOd.setDatumNedjelja(LocalDate.of(2000, 1, 9));
		sdOd.setDatumPetak(LocalDate.of(2000, 1, 7));
		sdOd.setMjesec(smDo);
		sdOd=kodRepository.getSifarnikDatumaRepository().save(sdOd);
		pi.setOk(pi.getOk() + ", SifarnikDatuma");
		
		OsobaValuta ov=new OsobaValuta();
		ov.setBand("B7");
		ov.setCijena(new BigDecimal(200));
		ov.setSifarnikDatumaDo(sdDo);
		ov.setSifarnikDatumaOd(sdOd);
		ov.setSifarnikOsoba(so);
		ov.setSifarnikValuta(sv);
		ov.setTs(0l);
		ov=kodRepository.getOsobaValutaRepository().save(ov);
		pi.setOk(pi.getOk() + ", OsobaValuta");
		
		SifarnikPodugovaraca sp=new SifarnikPodugovaraca();
		sp.setNaziv("Podugovarač");
		sp=kodRepository.getSifarnikPodugovaracaRepository().save(sp);
		pi.setOk(pi.getOk() + ", SifarnikPodugovaraca");
		
		Projekt p=new Projekt();
		p.setClaim("Claim");
		p.setContract("Contract");
		p.setStatus(DbStatus.A);
		p.setTs(0l);
		p=kodRepository.getProjektRepository().save(p);
		pi.setOk(pi.getOk() + ", Projekt");
					
		ProjektDetalji pd=new ProjektDetalji();
		pd.setCostActual(BigDecimal.ZERO);
		pd.setCostPlanned(BigDecimal.ZERO);
		pd.setCostPs(BigDecimal.TEN);
		pd.setProjekt(p);
		pd.setSifarnikValuta(sv);
		pd.setTotalRevenue(new BigDecimal(123456));
		pd=kodRepository.getProjektDetaljiRepository().save(pd);
		pi.setOk(pi.getOk() + ", ProjektDetalji");
		
		Claim c=new Claim();
		c.setOsobaClaimActual(BigDecimal.ZERO);
		c.setOsobaClaimPlanned(BigDecimal.ZERO);
		c.setProjektDetalji(pd);
		c.setSifarnikOsoba(so);
		c=kodRepository.getClaimRepository().save(c);
		pi.setOk(pi.getOk() + ", Claim");
		
		OsobaClaimActual oca=new OsobaClaimActual();
		oca.setCijena(BigDecimal.ONE);
		oca.setClaim(c);
		oca.setOsobaValuta(ov);
		oca.setSati(BigDecimal.ONE);
		oca.setSifarnikDatuma(sdOd);
		oca=kodRepository.getOsobaClaimActualRepository().save(oca);
		pi.setOk(pi.getOk() + ", OsobaClaimActual");
		
		OsobaClaimPlanned ocp=new OsobaClaimPlanned();
		ocp.setClaim(c);
		ocp.setSati(BigDecimal.ONE);
		ocp.setSifarnikMjeseca(smOd);
		ocp=kodRepository.getOsobaClaimPlannedRepository().save(ocp);
		pi.setOk(pi.getOk() + ", OsobaClaimPlanned");
		
		ClaimPodugovarac cp=new ClaimPodugovarac();
		cp.setActual(BigDecimal.ZERO);
		cp.setPlanned(BigDecimal.ZERO);
		cp.setPo("1234");
		cp.setProjektDetalji(pd);
		cp.setSifarnikPodugovaraca(sp);
		cp.setTotal(BigDecimal.ZERO);
		cp=kodRepository.getClaimPodugovaracRepository().save(cp);
		pi.setOk(pi.getOk() + ", ClaimPodugovarac");
		
		return pi;
	}

}
