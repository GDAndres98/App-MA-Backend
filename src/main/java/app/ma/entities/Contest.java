package app.ma.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "contest")
public class Contest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column
	private String password;
	@Column(nullable = false)
	private boolean isPrivate;
	@Column(nullable = false)
	private boolean isVisible;
	@Column(nullable = false)
	private Date startTime;
	@Column(nullable = false)
	private Date durationTime;
	@Column(nullable = false)
	private boolean isPartialVerdict;

	// TODO Problem-Contest

    @OneToMany(mappedBy = "contest")
    @JsonIgnore
    Set<ProblemContest> problemContest = new HashSet<>();


	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Contest() {

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public void setPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getDurationTime() {
		return durationTime;
	}

	public void setDurationTime(Date durationTime) {
		this.durationTime = durationTime;
	}

	public boolean isPartialVerdict() {
		return isPartialVerdict;
	}

	public void setPartialVerdict(boolean isPartialVerdict) {
		this.isPartialVerdict = isPartialVerdict;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void addProblemContest(ProblemContest problemContest2) {
		this.problemContest.add(problemContest2);
	}

	public Set<ProblemContest> getProblemContest() {
		return problemContest;
	}

	public void setProblemContest(Set<ProblemContest> problemContest) {
		this.problemContest = problemContest;
	}
}