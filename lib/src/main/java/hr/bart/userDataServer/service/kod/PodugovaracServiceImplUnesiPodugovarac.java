package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.db.Podugovarac;
import hr.bart.userDataServer.db.ProjektDetalji;
import hr.bart.userDataServer.util.PojoInterface;

public class PodugovaracServiceImplUnesiPodugovarac extends Kod {
	private final Optional<Long> id;
	private final Long ts;
	private final Long idProjektDetalji;
	private final Optional<Long> idPurchaseOrder;
	private final LocalDate datumPlanned;
	private final LocalDate datumActual;
	private final Optional<BigDecimal> cijena;
	private final Optional<Long> invoiceNumber;
	private ACommonServis aCommonServis=new ACommonServis(getKodRepository());

	public PodugovaracServiceImplUnesiPodugovarac(
			KodRepository kodRepository,
			Optional<Long> id,
			Long ts,
			Long idProjektDetalji,
			Optional<Long> idPurchaseOrder,
			LocalDate datumPlanned,
			LocalDate datumActual,
			Optional<BigDecimal> cijena,
			Optional<Long> invoiceNumber
			) {
		super(kodRepository);
		this.id=id;
		this.ts=ts;
		this.idProjektDetalji=idProjektDetalji;
		this.idPurchaseOrder=idPurchaseOrder;
		this.datumPlanned=datumPlanned;
		this.datumActual=datumActual;
		this.cijena=cijena;
		this.invoiceNumber=invoiceNumber;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) throws Throwable {		
		if(!idPurchaseOrder.isPresent()) {
			String msg="'Purchase order' nije odababran. Odaberite ga.";
			pi.setGreskaListString(msg);
		}
		
		if(datumPlanned==null) {
			String msg="'Planirani datum' nije unesen. Unesite ga.";
			pi.setGreskaListString(msg);
		} else if(datumActual!=null) {
			if(datumActual.isBefore(datumPlanned)) {
				String msg="'Aktualni datum' je prije 'Planirani datum'. Može biti ili jednaki ili kasniji.";
				pi.setGreskaListString(msg);
			}
		}
		
		if(!invoiceNumber.isPresent()) {
			String msg="'Invoice number' nije unesen. Unesite ga.";
			pi.setGreskaListString(msg);
		}
		
		if(!cijena.isPresent()) {
			String msg="'Cijena' nije unesena. Unesite je.";
			pi.setGreskaListString(msg);
		}
		
		if(pi.getGreska().isEmpty()) {
//			Optional<ProjektDetalji> projektDetaljiO=projektDetaljiRepository.findById(idProjektDetalji);
			Optional<ClaimPodugovarac> claimPodugovaracOrderO=getKodRepository().getClaimPodugovaracRepository().findById(idPurchaseOrder.get());
			
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
			
			podugovarac=getKodRepository().getPodugovaracRepository().save(podugovarac);
			
			setTableProjektDetalji(idProjektDetalji);
			
			Optional<List<Podugovarac>> podugovaracListO=getKodRepository().getPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
			
			if(podugovaracListO.isPresent()) {
				pi.setRezultat(podugovaracListO.get());
			} 
		} else {
			
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
		
		Optional<List<Podugovarac>> podugovaracListO=getKodRepository().getPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
	
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
			
			Optional<List<Podugovarac>> pListO=getKodRepository().getPodugovaracRepository().findAllByIdClaimPodugovarac(idClaimPodugovarac);
			
			if(pListO.isPresent()) {
				for(Podugovarac p: pListO.get()) {
					pBD=pBD.add(p.getCijena());
					
					if(p.getDatumActual()!=null) {
						aBD=aBD.add(p.getCijena());
					}
				}
				
				Optional<ClaimPodugovarac> claimPodugovaracOrderO=getKodRepository().getClaimPodugovaracRepository().findById(idClaimPodugovarac);
				
				claimPodugovaracOrderO.get().setPlanned(pBD);
				claimPodugovaracOrderO.get().setActual(aBD);
				
				getKodRepository().getClaimPodugovaracRepository().save(claimPodugovaracOrderO.get());
			}
		}
		
		Optional<ProjektDetalji> projektDetaljiO=getKodRepository().getProjektDetaljiRepository().findById(idProjektDetalji);
		projektDetaljiO.get().setCostPlanned(osobaClaimPlannedKn);
		projektDetaljiO.get().setCostActual(osobaClaimActualKn);

		getKodRepository().getProjektDetaljiRepository().save(projektDetaljiO.get());
	}

}
