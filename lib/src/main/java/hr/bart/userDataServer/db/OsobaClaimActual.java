package hr.bart.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
//@JsonIdentityInfo( generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@osobaClaimId", scope = OsobaClaimActual.class )
public class OsobaClaimActual implements Serializable {
	private static final long serialVersionUID = 4566929710485080026L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private BigDecimal sati;
	private BigDecimal cijena;
	private BigDecimal cijenaTecaj;
	@Version
	private Long ts;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_tecaj", referencedColumnName="id")
	private SifarnikTecaj sifarnikTecaj;
	@ManyToOne
	@JoinColumn(name = "id_claim", referencedColumnName="id")
	private Claim claim;
//	private Long idClaim;
	@ManyToOne
	@JoinColumn(name = "id_osoba_valuta", referencedColumnName = "id")
	private OsobaValuta osobaValuta;
	@ManyToOne
	@JoinColumn(name = "id_sifarnik_datuma", referencedColumnName = "id")
	private SifarnikDatuma sifarnikDatuma;
	
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
	
	public BigDecimal getCijena() {
		return cijena;
	}
	
	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}
	
	public BigDecimal getCijenaTecaj() {
		return cijenaTecaj;
	}
	
	public void setCijenaTecaj(BigDecimal cijenaTecaj) {
		this.cijenaTecaj = cijenaTecaj;
	}
	
	public Long getTs() {
		return ts;
	}
	
	public void setTs(Long ts) {
		this.ts = ts;
	}
	
	public SifarnikTecaj getSifarnikTecaj() {
		return sifarnikTecaj;
	}
	
	public void setSifarnikTecaj(SifarnikTecaj sifarnikTecaj) {
		this.sifarnikTecaj = sifarnikTecaj;
	}
	
	public Claim getClaim() {
		return claim;
	}
	
	public void setClaim(Claim claim) {
		this.claim = claim;
	}
	
//	public Long getIdClaim() {
//		return idClaim;
//	}
//	
//	public void setIdClaim(Long claimId) {
//		this.idClaim = claimId;	
//	}

	public OsobaValuta getOsobaValuta() {
		return osobaValuta;
	}

	public void setOsobaValuta(OsobaValuta osobaValuta) {
		this.osobaValuta = osobaValuta;
	}

//	public Long getClaimId() {
//		return claimId;
//	}
//
//	public void setClaimId(Long claimId) {
//		this.claimId = claimId;
//	}

	public SifarnikDatuma getSifarnikDatuma() {
		return sifarnikDatuma;
	}

	public void setSifarnikDatuma(SifarnikDatuma sifarnikDatuma) {
		this.sifarnikDatuma = sifarnikDatuma;
	}
}
