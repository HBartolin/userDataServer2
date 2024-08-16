package hr.bart.userDataServer.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.http.ResponseEntity;

import hr.bart.userDataServer.util.PojoInterface;

public abstract class ResponseEntityPojo {
	public static final String REQUEST_MAPPING_VALUE="/api";
	protected DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("y-M-d");
	
	protected ResponseEntity<PojoInterface> handlePi(PojoInterface pi) {
		if(!pi.getGreska().isEmpty()) {
			return ResponseEntity.badRequest().body(pi);
		}
		
		return ResponseEntity.ok(pi);
	}
}
