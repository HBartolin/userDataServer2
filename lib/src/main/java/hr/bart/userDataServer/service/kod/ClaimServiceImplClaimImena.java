package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import hr.bart.userDataServer.util.PojoInterface;

public class ClaimServiceImplClaimImena extends Kod {
	private final Optional<List<Long>> podatciO;

	public ClaimServiceImplClaimImena(Optional<List<Long>> podatciO) {
		this.podatciO=podatciO;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi, Object... o) {	
		if(podatciO.isPresent()) {
			List<Long> podatci=podatciO.get();
		} else {
			
		}
		
		return pi;
	}

}
