package hr.kyndryl.bartolin.userDataServer.db;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
public class Podugovarac implements Serializable {
	private static final long serialVersionUID = 6106097811232013619L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Version
	private Long ts;
	private LocalDate datumPlanned;
	private LocalDate datumActual;
	private BigDecimal cijena;
	private Long invoiceNumber;
	@ManyToOne
	@JoinColumn(name = "id_claim_podugovarac", referencedColumnName="id")
	private ClaimPodugovarac claimPodugovarac;
	
	public Long getTs() {
		return ts;
	}
	
	public void setTs(Long ts) {
		this.ts = ts;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getDatumPlanned() {
		return datumPlanned;
	}
	
	public void setDatumPlanned(LocalDate datumPlanned) {
		this.datumPlanned = datumPlanned;
	}

	public LocalDate getDatumActual() {
		return datumActual;
	}

	public void setDatumActual(LocalDate datumActual) {
		this.datumActual = datumActual;
	}

	public BigDecimal getCijena() {
		return cijena;
	}

	public void setCijena(BigDecimal cijena) {
		this.cijena = cijena;
	}

	public Long getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Long invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public ClaimPodugovarac getClaimPodugovarac() {
		return claimPodugovarac;
	}

	public void setClaimPodugovarac(ClaimPodugovarac purchaseOrder) {
		this.claimPodugovarac = purchaseOrder;
	}
}
