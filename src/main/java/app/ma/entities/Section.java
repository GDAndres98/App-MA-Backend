package app.ma.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "section_article", joinColumns = @JoinColumn(name = "section_id"), inverseJoinColumns = @JoinColumn(name = "article_id"))
	@JsonIgnoreProperties({"tags"})
	@OrderBy("title")
	private Set<Article> articles = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "posted_at", nullable = false)
	private Course postedAt;

	@Column
	private Long orderSection;
	
	@Column(length = 4000)
	private String attached;
	
	@ManyToMany
	@JoinColumn(nullable = true)
	@JsonIgnoreProperties({"tags"})
	@OrderBy("title")
	private Set<Problem> problems;
	

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

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void addArticle(Article article) {
		articles.add(article);
	}

	public void deleteArticle(Article article) {
		this.articles.remove(article);
	}

	public Long getOrderSection() {
		return orderSection;
	}

	public void setOrderSection(Long orderSection) {
		this.orderSection = orderSection;
	}

	public String getAttached() {
		return attached;
	}

	public void setAttached(String attached) {
		this.attached = attached;
	}

	public Set<Problem> getProblems() {
		return problems;
	}

	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

	public void addProblem(Problem problem) {
		this.problems.add(problem);
	}

	public void deleteProblem(Problem problem) {
		this.problems.remove(problem);
	}


}