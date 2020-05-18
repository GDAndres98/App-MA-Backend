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

@Entity
@Table(name = "submit")
public class Submit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String sourceCodeURL;
	@Column(nullable = false)
	private String Language;
	@Column(nullable = false)
	private String Verdict;
	@Column(nullable = false)
	private Long TimeConsumed;
	@Column(nullable = false)
	private Long MemoryConsumed;
	@Column(nullable = false)
	private Date submitDate;
	@Column(nullable = false)
	private Long casePassed;

	// TODO Prob-Cont-User Prob
	// TODO Prob-Cont-User Cont
	// TODO Prob-Cont-User User

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

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

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getVerdict() {
		return Verdict;
	}

	public void setVerdict(String verdict) {
		Verdict = verdict;
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
}