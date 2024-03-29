package app.ma.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 4000)
	private String title;
	@Column(nullable = false, length = 4000)
	private String content;
	@Column(nullable = false)
	private Date creationDate = new Date(System.currentTimeMillis());

	@ManyToOne
	@JoinColumns({ 
		@JoinColumn(name = "student_id", referencedColumnName = "student_id"),
		@JoinColumn(name = "course_id", referencedColumnName = "course_id"), })
	@JsonIgnoreProperties({ "course" })
	private UserCourse userCourse;

	@OneToOne // TODO One to one
	@JoinColumn(name = "parent", nullable = true)
	@JsonIgnore
	private Post parent;

	@Formula(" (select count(*) from post c where c.parent =id) ")
	public long repliesCount;

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Post() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Post getParent() {
		return parent;
	}

	public UserCourse getUserCourse() {
		return userCourse;
	}

	public void setUserCourse(UserCourse userCourse) {
		this.userCourse = userCourse;
	}

	public void setParent(Post parent) {
		this.parent = parent;
	}

	public long getRepliesCount() {
		return repliesCount;
	}

	public void setRepliesCount(long repliesCount) {
		this.repliesCount = repliesCount;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

}