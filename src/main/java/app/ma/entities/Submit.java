package app.ma.entities;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import app.ma.enums.Language;
import app.ma.enums.Veredict;

@Entity
@Table(name = "submit")
public class Submit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 4000)
	@JsonIgnore
	private String sourceCodeURL;
	@Column(nullable = false)
	private Language language;
	@Column(nullable = false)
	private Veredict veredict;
	@Column()
	private Long TimeConsumed;
	@Column()
	private Long MemoryConsumed;
	@Column(nullable = false)
	private Date submitDate = new Date(System.currentTimeMillis());
	@Column()
	private Long casePassed;
	
	// TODO Prob-Cont-User Prob
	// TODO Prob-Cont-User Cont
	// TODO Prob-Cont-User User
	


	@ManyToOne
	@JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id"),
        @JoinColumn(name = "problem_id", referencedColumnName = "problem_id"),
        @JoinColumn(name = "contest_id", referencedColumnName = "contest_id"),
        })
	@JsonIgnore
	private ProblemContestUser problemContestUser;
	
	
	

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	@JsonIgnoreProperties(value = {"author", "markdown", "tags"})
	public Problem getProblem() {
		return this.problemContestUser.problemContest.problem;
	}
	
	
	public String getUsername() {
		return this.problemContestUser.user.getUsername();
	}

	
	
	
	// ****************************************************************

	// -------------------------------------------------------------------

	public Submit() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceCodeURL() {
		return sourceCodeURL;
	}

	public void setSourceCodeURL(String sourceCodeURL) {
		this.sourceCodeURL = sourceCodeURL;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}


	public Veredict getVeredict() {
		return veredict;
	}


	public void setVeredict(Veredict veredict) {
		this.veredict = veredict;
	}


	public Long getTimeConsumed() {
		return TimeConsumed;
	}

	public void setTimeConsumed(Long timeConsumed) {
		TimeConsumed = timeConsumed;
	}

	public Long getMemoryConsumed() {
		return MemoryConsumed;
	}

	public void setMemoryConsumed(Long memoryConsumed) {
		MemoryConsumed = memoryConsumed;
	}

	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	public Long getCasePassed() {
		return casePassed;
	}

	public void setCasePassed(Long casePassed) {
		this.casePassed = casePassed;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public ProblemContestUser getProblemContestUser() {
		return problemContestUser;
	}

	public void setProblemContestUser(ProblemContestUser problemContestUser) {
		this.problemContestUser = problemContestUser;
	}
}