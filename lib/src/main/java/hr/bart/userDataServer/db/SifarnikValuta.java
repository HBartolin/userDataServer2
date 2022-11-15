package hr.bart.userDataServer.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table
@DynamicInsert
@DynamicUpdate
public class SifarnikValuta implements Serializable {
	private static final long serialVersionUID = -3438467538285407488L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String naziv;
	
	public Long getId() {
        return id;
    }

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	
}
