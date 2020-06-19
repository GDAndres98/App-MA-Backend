package app.ma.compositeKey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProblemContestKey implements Serializable{

	@Column(name = "problem_id")
    Long problemId;
 
    @Column(name = "contest_id")
    Long contestId;

    

	// ****************************************************************

	// -------------------------------------------------------------------

    public ProblemContestKey() {}

    public ProblemContestKey(Long problemId, Long contestId) {
    	setProblemId(problemId);
    	setContestId(contestId);
	}
	
	
    public Long getProblemId() {
		return problemId;
	}

	public void setProblemId(Long problemId) {
		this.problemId = problemId;
	}

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ProblemContestKey)) return false;
        
        ProblemContestKey o = (ProblemContestKey) obj;
        
        if(this.problemId != null? !this.problemId.equals(o.problemId): o.problemId != null)
            return false;
        if(this.contestId != null? !this.contestId.equals(o.contestId): o.contestId != null)
            return false;
        
        return true;
    }
    
    public int hashCode() {
        int result;
        result = (problemId != null ? problemId.hashCode() : 0);
        result = 47 * result + (contestId != null ? contestId.hashCode() : 0);
        return result;
    }
}
