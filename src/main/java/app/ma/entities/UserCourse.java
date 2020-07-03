package app.ma.entities;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.ma.compositeKey.UserCourseKey;

@Entity
public class UserCourse {
	
	@EmbeddedId
	UserCourseKey id;

	@ManyToOne
    @MapsId("student_id")
    @JoinColumn(name = "student_id")
    User student;
 
    @ManyToOne
    @MapsId("course_id")
    @JoinColumn(name = "course_id")
    Course course;
    
    @OneToMany(mappedBy="userCourse", cascade = CascadeType.REMOVE)
	@JsonIgnore
    private Set<Post> post;
    
  
    
	// ****************************************************************

	// -------------------------------------------------------------------

    
    
    public void setId(UserCourseKey id) {
		this.id = id;
	}
    
	public Set<Post> getPost() {
		return post;
	}

	public void setPost(Set<Post> post) {
		this.post = post;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public UserCourseKey getId() {
		return id;
	}
    
    
    
}
