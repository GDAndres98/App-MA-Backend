package app.ma.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "course")
public class Course {
	// TODO User-Class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String logoUrl;

	@ManyToOne	
	@JoinColumn(name = "professor_id", nullable = false)
	private User professor;
	

    @OneToMany(mappedBy = "course")
    Set<UserCourse> students;
	
    

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Course() {		

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoUrl() {
		return logoUrl;
	}

	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}

	public User getProfessor() {
		return professor;
	}

	public void setProfessor(User professor) {
		this.professor = professor;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

}