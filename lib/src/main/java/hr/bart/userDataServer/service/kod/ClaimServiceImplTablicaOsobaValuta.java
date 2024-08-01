package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hr.bart.userDataServer.db.Claim;
import hr.bart.userDataServer.repository.ClaimRepository;
import hr.bart.userDataServer.repository.OsobaClaimActualRepository;
import hr.bart.userDataServer.repository.OsobaClaimPlannedRepository;
import hr.bart.userDataServer.util.PojoInterface;

public class ClaimServiceImplTablicaOsobaValuta extends Kod {
	private final Long idProjektDetalji;
	private ACommonServis aCommonServis=new ACommonServis();
	private final ClaimRepository claimRepository;
	private final OsobaClaimActualRepository osobaClaimActualRepository;
	private final OsobaClaimPlannedRepository osobaClaimPlannedRepository;

	public ClaimServiceImplTablicaOsobaValuta(
			ClaimRepository claimRepository, 
			OsobaClaimActualRepository osobaClaimActualRepository,
			OsobaClaimPlannedRepository osobaClaimPlannedRepository,
			Long idProjektDetalji) {
		this.idProjektDetalji=idProjektDetalji;
		this.claimRepository = claimRepository;
		this.osobaClaimActualRepository = osobaClaimActualRepository;
		this.osobaClaimPlannedRepository = osobaClaimPlannedRepository;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {		
		Optional<List<Claim>> claimListOptional=aCommonServis.claimActualPlanned(
				claimRepository,
				osobaClaimActualRepository,
				osobaClaimPlannedRepository,
				idProjektDetalji);
		
		if(claimListOptional.isPresent()) {
			pi.setRezultat(claimListOptional.get());
		}
		
		return pi;
	}
	
	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
