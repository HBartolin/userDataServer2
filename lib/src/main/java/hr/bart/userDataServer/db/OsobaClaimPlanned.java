package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class OsobaClaimPlanned implements Serializable {
	private static final long serialVersionUID = -7567247031423063526L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private BigDecimal sati;
	@Version
	private Long ts;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_mjeseca", referencedColumnName="id")
	private SifarnikMjeseca sifarnikMjeseca;
	@ManyToOne
	@JoinColumn(name = "id_claim", referencedColumnName="id")
	private Claim claim;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public BigDecimal getSati() {
		return sati;
	}
	
	public void setSati(BigDecimal sati) {
		this.sati = sati;
	}
	
	public Long getTs() {
		return ts;
	}
	
	public void setTs(Long ts) {
		this.ts = ts;
	}
	
	public SifarnikMjeseca getSifarnikMjeseca() {
		return sifarnikMjeseca;
	}
	
	public void setSifarnikMjeseca(SifarnikMjeseca sifarnikMjeseca) {
		this.sifarnikMjeseca = sifarnikMjeseca;
	}
	
	public Claim getClaim() {
		return claim;
	}
	
	public void setClaim(Claim claim) {
		this.claim = claim;
	}
}
