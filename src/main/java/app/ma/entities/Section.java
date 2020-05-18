package app.ma.entities;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "section")
public class Section {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String description;

	@ManyToMany
	@JoinTable(name = "section_article", joinColumns = @JoinColumn(name = "section_id"), inverseJoinColumns = @JoinColumn(name = "article_id"))
	private Set<Article> articles;

	@ManyToOne
	@JoinColumn(name = "posted_at", nullable = false)
	private Course postedAt;

	@OneToOne // TODO One to one
	@JoinColumn(nullable = false)
	private Contest problems;

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Section() {

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Course getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Course postedAt) {
		this.postedAt = postedAt;
	}

	public Contest getProblems() {
		return problems;
	}

	public void setProblems(Contest problems) {
		this.problems = problems;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

}