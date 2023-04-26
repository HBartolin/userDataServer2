package hr.bart.userDataServer.db;

import java.io.Serializable;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import hr.bart.userDataServer.util.DbStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class Projekt implements Serializable {
	private static final long serialVersionUID = -8111180930112934554L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String claim;
	private String contract;
	@Version
	private Long ts;
	@Enumerated(EnumType.STRING)
	private DbStatus status; 
	
	@Override
    public String toString() {
		return String.format("Projekt {id=%d, claim='%s', contract='%s', status=%s, ts=%s}", id, claim, contract, status, ts);
    }
	
	public void setId(Long id) {
		this.id=id;
	}
	
	public Long getId() {
        return id;
    }

	public String getClaim() {
		return claim;
	}

	public void setClaim(String claim) {
		this.claim = claim;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Long getTs() {
		return ts;
	}

	public void setTs(Long ts) {
		this.ts = ts;
	}

	public DbStatus getStatus() {
		return status;
	}

	public void setStatus(DbStatus status) {
		this.status = status;
	}

}
