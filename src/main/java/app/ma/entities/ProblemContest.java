package app.ma.entities;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import app.ma.compositeKey.ProblemContestKey;

@Entity
public class ProblemContest {

	@EmbeddedId
	ProblemContestKey id;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("problem_id")
    @JoinColumn(name = "problem_id")
    Problem problem;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("contest_id")
    @JoinColumn(name = "contest_id")
    Contest contest;
    
    @OneToMany(mappedBy = "problemContest")
    Set<ProblemContestUser> problemContestUser;
    
   
	@Column(nullable = false)
	private Long problemTestCases = 0l;
	
	@Column(nullable = false)
	private Long problemDificulty = 0l;
    
    
	public Contest getContest() {
		return contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

    
	public ProblemContestKey getId() {
		return id;
	}

	public void setId(ProblemContestKey id) {
		this.id = id;
	}




    
//    @OneToMany(mappedBy="userCourse")
//    private Set<Post> post;
}
