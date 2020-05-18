package app.ma.compositeKey;

import java.io.Serializable;

import javax.persistence.Column;

public class UserCourseKey implements Serializable{

	@Column(name = "student_id")
    Long studentId;
 
    @Column(name = "course_id")
    Long courseId;


	// ****************************************************************

	// -------------------------------------------------------------------

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
	
	
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof UserCourseKey)) return false;
        
        UserCourseKey o = (UserCourseKey) obj;
        
        if(this.studentId != null? !this.studentId.equals(o.studentId): o.studentId != null)
            return false;
        if(this.courseId != null? !this.courseId.equals(o.courseId): o.courseId != null)
            return false;
        
        return true;
    }
    
    public int hashCode() {
        int result;
        result = (studentId != null ? studentId.hashCode() : 0);
        result = 47 * result + (courseId != null ? courseId.hashCode() : 0);
        return result;
    }
}
