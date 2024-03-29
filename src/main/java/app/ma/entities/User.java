package app.ma.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "user")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Long.class)
public class User {
	// TODO User-Class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String username;

	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column
	private String profilePicUrl;

	@Column(unique = true, nullable = false)
	private String email;
	@Column(nullable = false)
	@JsonIgnore
	private String password;

	@Column(nullable = false, columnDefinition = "integer default 0")
	private Long rating = 0l;

	@OneToMany(mappedBy = "student")
	@JsonIgnore
	Set<UserCourse> courses;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	Set<UserStage> stages;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	Set<ProblemContestUser> problemContestUser;

	@ManyToMany(mappedBy = "users")
	@JsonIgnore
	private Set<Role> role = new HashSet<>();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JsonIgnore
    private Set<Course> professorClass = new HashSet<>();



	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ********************************************************************

	// -------------------------------------------------------------------------

	public User() {
	}

	public User(String username, String firstName, String lastName, String profilePicUrl, String email,
			String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profilePicUrl = profilePicUrl;
		this.email = email;
		this.password = password;
	}

	public User(String username) {
		this.username = username;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRating() {
		return rating;
	}

	public void setRating(Long rating) {
		this.rating = rating;
	}

	public String getProfilePicUrl() {
		return profilePicUrl;
	}

	public void setProfilePicUrl(String profilePicUrl) {
		this.profilePicUrl = profilePicUrl;
	}

	public Set<UserCourse> getCourses() {
		return courses;
	}

	public void setCourses(Set<UserCourse> courses) {
		this.courses = courses;
	}

	public Set<ProblemContestUser> getProblemContestUser() {
		return problemContestUser;
	}

	public void setProblemContestUser(Set<ProblemContestUser> problemContestUser) {
		this.problemContestUser = problemContestUser;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public void addRole(Role rol) {
		this.role.add(rol);
	}
	
	public Set<Course> getProfessorClass() {
		return professorClass;
	}

	public void setProfessorClass(Set<Course> professorClass) {
		this.professorClass = professorClass;
	}

	public Boolean getAdmin() {
		Boolean b = false;
		for(Role role: this.role)
			b |= role.isAdmin();
		return b;
	}

	public boolean isProfesor() {
		Boolean b = false;
		for(Role role: this.role)
			b |= role.isProfessor();
		return b;
	}

	public boolean isStudent() {
		Boolean b = false;
		for(Role role: this.role)
			b |= role.isStudent();
		return b;
	}

	public void addCourse(Course course) {
		this.professorClass.add(course);
	}

	public Set<UserStage> getStages() {
		return stages;
	}

	public void setStages(Set<UserStage> stages) {
		this.stages = stages;
	}

	public void removeRole(Role profesor) {
		this.role.remove(profesor);
	}
	
}