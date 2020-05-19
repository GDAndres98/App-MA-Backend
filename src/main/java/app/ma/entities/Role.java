package app.ma.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "role")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;



	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean createUser = false;
	
	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isProfessor = false;



	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ********************************************************************

	// -------------------------------------------------------------------------

	public Role() {
	}
	

	public boolean isProfessor() {
		return isProfessor;
	}

	public void setProfessor(boolean isProfessor) {
		this.isProfessor = isProfessor;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

}