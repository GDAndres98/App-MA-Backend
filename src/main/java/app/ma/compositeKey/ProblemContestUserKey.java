package app.ma.compositeKey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;

import org.hibernate.annotations.Columns;

import app.ma.entities.ProblemContest;

@Embeddable
public class ProblemContestUserKey implements Serializable{

	@Column(name = "user_id")
    Long userId;

	@Column(name = "problem_contest_id")
    ProblemContestKey problemContest;

    

	// ****************************************************************

	// -------------------------------------------------------------------

	
	

	


	@Override
    public boolean equals(Object obj) {
        if(!(obj instanceof ProblemContestUserKey)) return false;
        
        ProblemContestUserKey o = (ProblemContestUserKey) obj;
        
        if(this.userId != null? !this.userId.equals(o.userId): o.userId != null)
            return false;
        if(this.problemContest != null? !this.problemContest.equals(o.problemContest): o.problemContest != null)
            return false;
  
        return true;
    }
    
    public int hashCode() {
        int result;
        result = (userId != null ? userId.hashCode() : 0);
        result = 47 * result + (problemContest != null ? problemContest.hashCode() : 0);
        return result;
    }
}
