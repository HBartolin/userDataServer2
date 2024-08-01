package hr.bart.userDataServer.service.kod;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import hr.bart.userDataServer.util.PojoInterface;

public class ClaimServiceImplClaimImena extends Kod {
	private final Optional<List<Long>> podatciO;

	public ClaimServiceImplClaimImena(Optional<List<Long>> podatciO) {
		this.podatciO=podatciO;
	}

	@Override
	public PojoInterface izvrsiKod(PojoInterface pi) {	
		if(podatciO.isPresent()) {
			List<Long> podatci=podatciO.get();
		} else {
			
		}
		
		return pi;
	}

	@Override
	public String getToString() {
		return new ReflectionToStringBuilder(this, getStandardToStringStyle()).toString();
	}
}
