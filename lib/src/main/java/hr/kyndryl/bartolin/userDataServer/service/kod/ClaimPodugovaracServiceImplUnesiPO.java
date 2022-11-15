package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.db.ClaimPodugovarac;
import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class ClaimPodugovaracServiceImplUnesiPO extends Kod {
	private Optional<Long> idO=(Optional<Long>) hm.get("idO");
	private Optional<Long>tsO=(Optional<Long>) hm.get("tsO");
	private Long idProjektDetalji=(Long) hm.get("idProjektDetalji");
	private Long idSifarnikPodugovaraca=(Long) hm.get("idSifarnikPodugovaraca");
	private String po=(String) hm.get("po");
	private Optional<BigDecimal> totalO=(Optional<BigDecimal>) hm.get("totalO");

	public ClaimPodugovaracServiceImplUnesiPO(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
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
				Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_id_idSifarnikPodugovaraca=kodRepository.getClaimPodugovaracRepository().findAllByIdProjektDetalji_id_idSifarnikPodugovaraca(idProjektDetalji, idO.get(), idSifarnikPodugovaraca);
				
				if(findAllByIdProjektDetalji_id_idSifarnikPodugovaraca.isPresent()) {
					if(greska.length()>0) greska=greska + " <BR> ";
					greska=greska + "'Naziv' je već dodijeljen, uredite naziv izravno u tablici.";
				}
			} else {
				Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_idSifarnikPodugovaraca=kodRepository.getClaimPodugovaracRepository().findAllByIdProjektDetalji_idSifarnikPodugovaraca(idProjektDetalji, idSifarnikPodugovaraca);
				
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
			purchaseOrder.setProjektDetalji(kodRepository.getProjektDetaljiRepository().findById(idProjektDetalji).get());
			purchaseOrder.setSifarnikPodugovaraca(kodRepository.getSifarnikPodugovaracaRepository().findById(idSifarnikPodugovaraca).get());
			
			if(idO.isPresent()) {
				Optional<ClaimPodugovarac> claimPodugovaracO=kodRepository.getClaimPodugovaracRepository().findById(idO.get());					
				
				purchaseOrder.setId(idO.get());
				purchaseOrder.setActual(claimPodugovaracO.get().getActual());
				purchaseOrder.setPlanned(claimPodugovaracO.get().getPlanned());
			} else {
				purchaseOrder.setActual(BigDecimal.ZERO);
				purchaseOrder.setPlanned(BigDecimal.ZERO);
			}
			
			kodRepository.getClaimPodugovaracRepository().save(purchaseOrder);
			
			Optional<List<ClaimPodugovarac>> purchaseOrderListO=kodRepository.getClaimPodugovaracRepository().findAllByIdProjektDetalji(idProjektDetalji);
			
			if(purchaseOrderListO.isPresent()) {
				pi.setRezultat(purchaseOrderListO.get());
			}
		} else {
			pi.setGreska(greska);
		}
		
		return pi;
	}

}
