package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimPodugovaracServiceImplUnesiPO extends Kod {
	private final Optional<Long> idO;
	private final Optional<Long> tsO;
	private final Long idProjektDetalji;
	private final Long idSifarnikPodugovaraca;
	private final String po;
	private final Optional<BigDecimal> totalO;

	public ClaimPodugovaracServiceImplUnesiPO(
			KodRepository kodRepository, 
			Optional<Long> idO, 
			Optional<Long> tsO,
			Long idProjektDetalji,
			Long idSifarnikPodugovaraca,
			String po,
			Optional<BigDecimal> totalO
			) {
		super(kodRepository);
		this.idO=idO;
		this.tsO=tsO;
		this.idProjektDetalji=idProjektDetalji;
		this.idSifarnikPodugovaraca=idSifarnikPodugovaraca;
		this.po=po;
		this.totalO=totalO;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {		
		String greska="";
		
		if(idProjektDetalji==null) {
			if(greska.length()>0) greska=greska + " <BR> ";
			greska=greska + "Polje 'idProjektDetalji' nije upisano.";
		}
		
		if(!totalO.isPresent()) {
			if(greska.length()>0) greska=greska + " <BR> ";
			greska=greska + "Polje 'total' nije upisano.";
		}
		
		if(idSifarnikPodugovaraca==null) {
			if(greska.length()>0) greska=greska + " <BR> ";
			greska=greska + "Polje 'Naziv' nije upisano.";
		}
		
		if(po==null || "".equals(po.replaceAll("\\s",""))) {
			if(greska.length()>0) greska=greska + " <BR> ";
			greska=greska + "Polje 'PO' nije upisano.";
		}
		
		if(idSifarnikPodugovaraca!=null && idProjektDetalji!=null) {
			if(idO.isPresent()) {
				Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_id_idSifarnikPodugovaraca=getKodRepository().getClaimPodugovaracRepository().findAllByIdProjektDetalji_id_idSifarnikPodugovaraca(idProjektDetalji, idO.get(), idSifarnikPodugovaraca);
				
				if(findAllByIdProjektDetalji_id_idSifarnikPodugovaraca.isPresent()) {
					if(greska.length()>0) greska=greska + " <BR> ";
					greska=greska + "'Naziv' je već dodijeljen, uredite naziv izravno u tablici.";
				}
			} else {
				Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_idSifarnikPodugovaraca=getKodRepository().getClaimPodugovaracRepository().findAllByIdProjektDetalji_idSifarnikPodugovaraca(idProjektDetalji, idSifarnikPodugovaraca);
				
				if(findAllByIdProjektDetalji_idSifarnikPodugovaraca.isPresent()) {
					if(greska.length()>0) greska=greska + " <BR> ";
					greska=greska + "'Naziv' je već dodijeljen, uredite naziv izravno u tablici.";
				}
			}
		}
		
		if(greska.isEmpty()) {
			ClaimPodugovarac purchaseOrder=new ClaimPodugovarac();
			purchaseOrder.setTs(tsO.get());
			purchaseOrder.setTotal(totalO.get());
			purchaseOrder.setPo(po);
			purchaseOrder.setProjektDetalji(getKodRepository().getProjektDetaljiRepository().findById(idProjektDetalji).get());
			purchaseOrder.setSifarnikPodugovaraca(getKodRepository().getSifarnikPodugovaracaRepository().findById(idSifarnikPodugovaraca).get());
			
			if(idO.isPresent()) {
				Optional<ClaimPodugovarac> claimPodugovaracO=getKodRepository().getClaimPodugovaracRepository().findById(idO.get());					
				
				purchaseOrder.setId(idO.get());
				purchaseOrder.setActual(claimPodugovaracO.get().getActual());
				purchaseOrder.setPlanned(claimPodugovaracO.get().getPlanned());
			} else {
				purchaseOrder.setActual(BigDecimal.ZERO);
				purchaseOrder.setPlanned(BigDecimal.ZERO);
			}
			
			getKodRepository().getClaimPodugovaracRepository().save(purchaseOrder);
			
			Optional<List<ClaimPodugovarac>> purchaseOrderListO=getKodRepository().getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
			
			if(purchaseOrderListO.isPresent()) {
				pi.setRezultat(purchaseOrderListO.get());
			}
		} else {
			pi.setGreska(greska);
		}
		
		return pi;
	}

}
