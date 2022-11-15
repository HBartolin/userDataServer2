package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.db.Podugovarac;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class PodugovaracServiceImplUnesiPodugovarac extends Kod {
	private Optional<Long> id=(Optional<Long>) hm.get("id");
	private Long ts=(Long) hm.get("ts");
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private Optional<Long> idPurchaseOrder=(Optional<Long>) hm.get("idPurchaseOrder");
	private LocalDate datumPlanned=(LocalDate) hm.get("datumPlanned");
	private LocalDate datumActual=(LocalDate) hm.get("datumActual");
	private Optional<BigDecimal> cijena=(Optional<BigDecimal>) hm.get("cijena");
	private Optional<Long> invoiceNumber=(Optional<Long>) hm.get("invoiceNumber");
	private ACommonServis aCommonServis=new ACommonServis(hm, kodRepository);

	public PodugovaracServiceImplUnesiPodugovarac(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {
		String greska="";
		
		if(!idPurchaseOrder.isPresent()) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="'Purchase order' nije odababran. Odaberite ga.";
		}
		
		if(datumPlanned==null) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="'Planirani datum' nije unesen. Unesite ga.";
		} else if(datumActual!=null) {
			if(datumActual.isBefore(datumPlanned)) {
				greska+="'Aktualni datum' je prije 'Planirani datum'. Može biti ili jednaki ili kasniji.";
			}
		}
		
		if(!invoiceNumber.isPresent()) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="'Invoice number' nije unesen. Unesite ga.";
		}
		
		if(!cijena.isPresent()) {
			if(greska.length()>0) greska+=" <BR> ";
			greska+="'Cijena' nije unesena. Unesite je.";
		}
		
		if(greska.isEmpty()) {
//			Optional<ProjektDetalji> projektDetaljiO=projektDetaljiRepository.findById(idProjektDetalji);
			Optional<ClaimPodugovarac> claimPodugovaracOrderO=kodRepository.getClaimPodugovaracRepository().findById(idPurchaseOrder.get());
			
			Podugovarac podugovarac=new Podugovarac();
			podugovarac.setCijena(cijena.get());
			podugovarac.setDatumActual(datumActual);
			podugovarac.setDatumPlanned(datumPlanned);
			podugovarac.setInvoiceNumber(invoiceNumber.get());
			podugovarac.setClaimPodugovarac(claimPodugovaracOrderO.get());
			podugovarac.setTs(ts);
			
			if(id.isPresent()) {
				podugovarac.setId(id.get());
			}
			
			podugovarac=kodRepository.getPodugovaracRepository().save(podugovarac);
			
			setTableProjektDetalji(idProjektDetalji);
			
			Optional<List<Podugovarac>> podugovaracListO=kodRepository.getPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
			
			if(podugovaracListO.isPresent()) {
				pi.setRezultat(podugovaracListO.get());
			} 
		} else {
			pi.setGreska(greska);
		}			
		
		return pi;
	}
	
	private void setTableProjektDetalji(Long idProjektDetalji) {
		BigDecimal osobaClaimPlannedKn=new BigDecimal(0);
		osobaClaimPlannedKn.setScale(2, RoundingMode.HALF_EVEN);
		BigDecimal osobaClaimActualKn=new BigDecimal(0);
		osobaClaimActualKn.setScale(2, RoundingMode.HALF_EVEN);	
		HashSet<Long> hashSet=new HashSet<Long>();
		
		Optional<List<Claim>> claimListO=aCommonServis.claimActualPlanned(idProjektDetalji);
		
		if(claimListO.isPresent()) {
			for(Claim c: claimListO.get()) {
				osobaClaimPlannedKn=osobaClaimPlannedKn.add(c.getOsobaClaimPlanned());
				osobaClaimActualKn=osobaClaimActualKn.add(c.getOsobaClaimActual());
			}
		}
		
		Optional<List<Podugovarac>> podugovaracListO=kodRepository.getPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
	
		if(podugovaracListO.isPresent()) {
			for(Podugovarac podugovarac: podugovaracListO.get()) {
				osobaClaimPlannedKn=osobaClaimPlannedKn.add(podugovarac.getCijena());
				
				if(podugovarac.getDatumActual()!=null) {
					osobaClaimActualKn=osobaClaimActualKn.add(podugovarac.getCijena());
				}
				
				hashSet.add(podugovarac.getClaimPodugovarac().getId());
			}
		}
		
		
		for(Long idClaimPodugovarac: hashSet) {
			BigDecimal aBD=BigDecimal.ZERO;
			BigDecimal pBD=BigDecimal.ZERO;
			
			Optional<List<Podugovarac>> pListO=kodRepository.getPodugovaracRepository().findAllByIdClaimPodugovarac(idClaimPodugovarac);
			
			if(pListO.isPresent()) {
				for(Podugovarac p: pListO.get()) {
					pBD=pBD.add(p.getCijena());
					
					if(p.getDatumActual()!=null) {
						aBD=aBD.add(p.getCijena());
					}
				}
				
				Optional<ClaimPodugovarac> claimPodugovaracOrderO=kodRepository.getClaimPodugovaracRepository().findById(idClaimPodugovarac);
				
				claimPodugovaracOrderO.get().setPlanned(pBD);
				claimPodugovaracOrderO.get().setActual(aBD);
				
				kodRepository.getClaimPodugovaracRepository().save(claimPodugovaracOrderO.get());
			}
		}
		
		Optional<ProjektDetalji> projektDetaljiO=kodRepository.getProjektDetaljiRepository().findById(idProjektDetalji);
		projektDetaljiO.get().setCostPlanned(osobaClaimPlannedKn);
		projektDetaljiO.get().setCostActual(osobaClaimActualKn);

		kodRepository.getProjektDetaljiRepository().save(projektDetaljiO.get());
	}

}
