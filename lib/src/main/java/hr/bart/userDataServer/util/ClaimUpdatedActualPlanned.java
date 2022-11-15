package hr.bart.userDataServer.util;

import java.math.BigDecimal;

public class ClaimUpdatedActualPlanned {
	private Long idSifarnikOsoba;
	private Long idClaim;
	private BigDecimal sati;
	private Long ts;

	@Override
	public String toString() {
		return String.format("{idSifarnikOsoba=%d, idClaim=%d, sati=%f, ts=%d}", idSifarnikOsoba, idClaim, sati, ts);
	}
	
	public BigDecimal getSati() {
		return sati;
	}

	public void setSati(BigDecimal sati) {
		this.sati = sati;
	}

	public Long getIdSifarnikOsoba() {
		return idSifarnikOsoba;
	}

	public void setIdSifarnikOsoba(Long idSifarnikOsoba) {
		this.idSifarnikOsoba = idSifarnikOsoba;
	}

	public Long getIdClaim() {
		return idClaim;
	}

	public void setIdClaim(Long idClaim) {
		this.idClaim = idClaim;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}
}
