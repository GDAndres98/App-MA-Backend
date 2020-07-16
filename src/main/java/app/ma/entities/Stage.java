package app.ma.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "stage")
public class Stage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false, length = 1000)
	private String description;
	@Column
	private String logo;
	@Column(nullable = false)
	private Long number;

    @OneToMany
	@JsonIgnore
    private Set<Level> levels = new HashSet<>();
    
	@OneToMany(mappedBy = "stage")
	@JsonIgnore
	Set<UserStage> users;
    
	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Stage() {

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getLogo() {
		return logo;
	}

	public Long getNumber() {
		return number;
	}

	public Set<Level> getLevels() {
		return levels;
	}

	public Set<UserStage> getUsers() {
		return users;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public void setLevels(Set<Level> levels) {
		this.levels = levels;
	}

	public void setUsers(Set<UserStage> users) {
		this.users = users;
	}
	
}