package hr.bart.userDataServer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.repository.OsobaValutaRepository;
import hr.bart.userDataServer.repository.PodugovaracRepository;
import hr.bart.userDataServer.repository.ProjektDetaljiRepository;
import hr.bart.userDataServer.service.kod.ExcelServiceImplUExcel;
import hr.bart.userDataServer.service.kod.KodRepository;
import hr.bart.userDataServer.util.PojoInterface;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ExcelServiceImpl  extends AService implements ExcelService {
	@Autowired
	private transient ProjektDetaljiRepository projektDetaljiRepository;
	@Autowired
	private transient OsobaValutaRepository osobaValutaRepository;
	@Autowired
	private transient PodugovaracRepository podugovaracRepository;
	@Autowired
	private transient ClaimRepository claimRepository;
	@Autowired
	private transient OsobaClaimActualRepository osobaClaimActualRepository;
	@Autowired
	private transient OsobaClaimPlannedRepository osobaClaimPlannedRepository;

	@Override
	public PojoInterface uExcel(Long id) {		
		return new ExcelServiceImplUExcel(getKodRepository(), id).izvrsi();
	}
	
	private KodRepository getKodRepository() {
		KodRepository kodRepository=new KodRepository();
		kodRepository.setProjektDetaljiRepository(projektDetaljiRepository);
		kodRepository.setOsobaValutaRepository(osobaValutaRepository);
		kodRepository.setPodugovaracRepository(podugovaracRepository);
		kodRepository.setClaimRepository(claimRepository);
		kodRepository.setOsobaClaimActualRepository(osobaClaimActualRepository);
		kodRepository.setOsobaClaimPlannedRepository(osobaClaimPlannedRepository);
		
		return kodRepository;
	}
	
}
