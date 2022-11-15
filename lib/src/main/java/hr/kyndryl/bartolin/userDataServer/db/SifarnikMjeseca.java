package hr.kyndryl.bartolin.userDataServer.db;

import java.io.Serializable;
import java.time.LocalDate;

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
public class SifarnikMjeseca implements Serializable {
	private static final long serialVersionUID = 1482875841526952054L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private LocalDate mjesec;
	
	public Long getId() {
        return id;
    }

	public LocalDate getMjesec() {
		return mjesec;
	}

	public void setMjesec(LocalDate mjesec) {
		this.mjesec = mjesec;
	}
	
}
