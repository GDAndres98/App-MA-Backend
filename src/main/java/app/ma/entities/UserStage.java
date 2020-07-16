package app.ma.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import app.ma.compositeKey.UserStageKey;

@Entity
public class UserStage {
	
	@EmbeddedId
	UserStageKey id;

	@ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    User user;
 
    @ManyToOne
    @MapsId("stage_id")
    @JoinColumn(name = "stage_id")
    Stage stage;
    
    Long levelNumber = 1l;
    
    
  
    
	// ****************************************************************

	// -------------------------------------------------------------------

    

	public void setId(UserStageKey id) {
		this.id = id;
	}
    

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public UserStageKey getId() {
		return id;
	}
    
    public Long getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(Long levelNumber) {
		this.levelNumber = levelNumber;
	}
    
}
