package app.ma.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "test_case")
public class TestCase {
	// TODO User-Class
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String tcInputURL;
	@Column(nullable = false)
	private String tcOutputURL;
	@Column
	private Long tcDifficulty;

	@ManyToOne
	@JsonIgnoreProperties({ "testCases" })
	@JoinColumn(name = "problem", nullable = false)
	private Problem problem;

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public TestCase() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTcInputURL() {
		return tcInputURL;
	}

	public void setTcInputURL(String tcInput) {
		this.tcInputURL = tcInput;
	}

	public String getTcOutputURL() {
		return tcOutputURL;
	}

	public void setTcOutputURL(String tcOutput) {
		this.tcOutputURL = tcOutput;
	}

	public Long getTcDifficulty() {
		return tcDifficulty;
	}

	public void setTcDifficulty(Long tcDifficulty) {
		this.tcDifficulty = tcDifficulty;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

}