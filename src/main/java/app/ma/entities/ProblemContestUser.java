package app.ma.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Formula;

import app.ma.compositeKey.ProblemContestUserKey;

@Entity
public class ProblemContestUser {

	@EmbeddedId
	ProblemContestUserKey id;

	@ManyToOne
	@MapsId("user_id")
	@JoinColumn(name = "user_id")
	User user;

	@ManyToOne
	@MapsId("problem_contest_id")
	@JoinColumns({ @JoinColumn(name = "problem_id", referencedColumnName = "problem_id"),
			@JoinColumn(name = "contest_id", referencedColumnName = "contest_id"), })
	ProblemContest problemContest;

	@OneToMany(mappedBy = "problemContestUser")
	private Set<Submit> submits;

	@Formula(" (select count(*) from submit b where b.user_id =user_id and b.problem_id =problem_id and b.contest_id =contest_id and b.veredict>1 and b.submit_date< COALESCE((select c.submit_date from submit c where c.user_id =user_id and c.problem_id =problem_id and c.contest_id =contest_id and c.veredict=1 order by c.submit_date limit 1), '3000-02-11' )) ")
	public Long tries;

	@Formula(" (select c.submit_date from submit c where c.user_id =user_id and c.problem_id =problem_id and c.contest_id =contest_id and c.veredict=1 order by c.submit_date limit 1) ")
	public Date solDate;

	public ProblemContestUser() {
	}

	public ProblemContestUser(Long userId, Long contestId, Long problemId) {
		this.setId(new ProblemContestUserKey(userId, contestId, problemId));
	}

	public ProblemContestUserKey getId() {
		return id;
	}

	public void setId(ProblemContestUserKey id) {
		this.id = id;
	}

	public ProblemContest getProblemContest() {
		return problemContest;
	}

	public void setProblemContest(ProblemContest problemContest) {
		this.problemContest = problemContest;
	}

	public Long getTries() {
		return this.tries;
	}
	
	public Date getSolDate() {
		return this.solDate;
	}
	
	public User getUser() {
		return user;
	}

}
