package hr.bart.userDataServer.service.kod;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import hr.bart.userDataServer.db.ClaimPodugovarac;
import hr.bart.userDataServer.repository.ClaimPodugovaracRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.repository.SifarnikPodugovaracaRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimPodugovaracServiceImplUnesiPO extends Kod {
	private final Optional<Long> idO;
	private final Optional<Long> tsO;
	private final Long idProjektDetalji;
	private final Long idSifarnikPodugovaraca;
	private final String po;
	private final Optional<BigDecimal> totalO;
	private final ClaimPodugovaracRepository claimPodugovaracRepository;
	private final ProjektDetaljiRepository projektDetaljiRepository;
	private final SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository;

	public ClaimPodugovaracServiceImplUnesiPO(
			ClaimPodugovaracRepository claimPodugovaracRepository, 
			ProjektDetaljiRepository projektDetaljiRepository,
			SifarnikPodugovaracaRepository sifarnikPodugovaracaRepository,
			Optional<Long> idO, 
			Optional<Long> tsO,
			Long idProjektDetalji,
			Long idSifarnikPodugovaraca,
			String po,
			Optional<BigDecimal> totalO
			) {
		this.claimPodugovaracRepository=claimPodugovaracRepository;
		this.projektDetaljiRepository=projektDetaljiRepository;
		this.sifarnikPodugovaracaRepository=sifarnikPodugovaracaRepository;
		this.idO=idO;
		this.tsO=tsO;
		this.idProjektDetalji=idProjektDetalji;
		this.idSifarnikPodugovaraca=idSifarnikPodugovaraca;
		this.po=po;
		this.totalO=totalO;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {				
		if(idProjektDetalji==null) {
			String msg="Polje 'idProjektDetalji' nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(!totalO.isPresent()) {
			String msg="Polje 'total' nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(idSifarnikPodugovaraca==null) {
			String msg="Polje 'Naziv' nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(po==null || "".equals(po.strip())) {
			String msg="Polje 'PO' nije upisano.";
			pi.addGreskaList(msg);
		}
		
		if(idSifarnikPodugovaraca!=null && idProjektDetalji!=null) {
			if(idO.isPresent()) {
/*				Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_id_idSifarnikPodugovaraca=claimPodugovaracRepository.findAllByIdProjektDetalji_id_idSifarnikPodugovaraca(idProjektDetalji, idO.get(), idSifarnikPodugovaraca);
				
				if(findAllByIdProjektDetalji_id_idSifarnikPodugovaraca.isPresent()) {
					String msg="'Naziv' je već dodijeljen, uredite naziv izravno u tablici.";
					pi.addGreskaList(msg);
				}
*/			} else {
				Optional<List<ClaimPodugovarac>> findAllByIdProjektDetalji_idSifarnikPodugovaraca=claimPodugovaracRepository.findAllByIdProjektDetalji_idSifarnikPodugovaraca(idProjektDetalji, idSifarnikPodugovaraca);
				
				if(findAllByIdProjektDetalji_idSifarnikPodugovaraca.isPresent()) {
					String msg="'Naziv' je već dodijeljen, uredite naziv izravno u tablici.";
					pi.addGreskaList(msg);
				}
			}
		}
		
		if(pi.getGreska().isEmpty()) {
			ClaimPodugovarac purchaseOrder=new ClaimPodugovarac();
			purchaseOrder.setTs(tsO.get());
			purchaseOrder.setTotal(totalO.get());
			purchaseOrder.setPo(po);
			purchaseOrder.setProjektDetalji(projektDetaljiRepository.findById(idProjektDetalji).get());
			purchaseOrder.setSifarnikPodugovaraca(sifarnikPodugovaracaRepository.findById(idSifarnikPodugovaraca).get());
			
			if(idO.isPresent()) {
				Optional<ClaimPodugovarac> claimPodugovaracO=claimPodugovaracRepository.findById(idO.get());					
				
				purchaseOrder.setId(idO.get());
				purchaseOrder.setActual(claimPodugovaracO.get().getActual());
				purchaseOrder.setPlanned(claimPodugovaracO.get().getPlanned());
			} else {
				purchaseOrder.setActual(BigDecimal.ZERO);
				purchaseOrder.setPlanned(BigDecimal.ZERO);
			}
			
			claimPodugovaracRepository.save(purchaseOrder);
			
			Optional<List<ClaimPodugovarac>> purchaseOrderListO=claimPodugovaracRepository.findAllByIdProjektDetalji(idProjektDetalji);
			
			if(purchaseOrderListO.isPresent()) {
				pi.setRezultat(purchaseOrderListO.get());
			}
		} else {
			
		}
		
		return pi;
	}

}
