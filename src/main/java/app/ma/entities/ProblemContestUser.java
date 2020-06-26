package app.ma.entities;

import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import app.ma.compositeKey.ProblemContestKey;
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
    @JoinColumns({
        @JoinColumn(name = "problem_id", referencedColumnName = "problem_id"),
        @JoinColumn(name = "contest_id", referencedColumnName = "contest_id"),
        })
    ProblemContest problemContest;
    
    @OneToMany(mappedBy="problemContestUser")
    private Set<Submit> submits;
    
    public ProblemContestUser() {}
    
	public ProblemContestUser(Long userId, Long contestId, Long problemId) {
		this.setId(new ProblemContestUserKey(userId, contestId, problemId));
	}
	
    public ProblemContestUserKey getId() {
		return id;
	}

	public void setId(ProblemContestUserKey id) {
		this.id = id;
	}


}
