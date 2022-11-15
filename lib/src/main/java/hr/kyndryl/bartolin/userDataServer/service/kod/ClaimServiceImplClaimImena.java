package hr.kyndryl.bartolin.userDataServer.service.kod;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import hr.kyndryl.bartolin.userDataServer.util.PojoInterface;

@SuppressWarnings("unchecked")
public class ClaimServiceImplClaimImena extends Kod {
	private Optional<List<Long>> podatciO=(Optional<List<Long>>) hm.get("podatciO");

	public ClaimServiceImplClaimImena(HashMap<String, Object> hm, KodRepository kodRepository) {
		super(hm, kodRepository);
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {	
		if(podatciO.isPresent()) {
			List<Long> podatci=podatciO.get();
		} else {
			
		}
		
		return pi;
	}

}
