package app.ma.compositeKey;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserStageKey implements Serializable{

	@Column(name = "user_id")
    Long userId;
 
    @Column(name = "stage_id")
    Long stageId;


	// ****************************************************************

	// -------------------------------------------------------------------

    public UserStageKey() {
	}
    
	public UserStageKey(Long stageId, Long userId) {
		setStageId(stageId);
		setUserId(userId);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}
	
	
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserStageKey)) return false;
        
        UserStageKey o = (UserStageKey) obj;
        
        if(this.userId != null? !this.userId.equals(o.userId): o.userId != null)
            return false;
        if(this.stageId != null? !this.stageId.equals(o.stageId): o.stageId != null)
            return false;
        
        return true;
    }
    
    public int hashCode() {
        int result;
        result = (userId != null ? userId.hashCode() : 0);
        result = 47 * result + (stageId != null ? stageId.hashCode() : 0);
        return result;
    }
}
