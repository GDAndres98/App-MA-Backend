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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tag")
public class Tag implements Comparable<Tag>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Long level;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tag_problem", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "problem_id"))
	@JsonIgnore
	private Set<Problem> problems = new HashSet<Problem>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "tag_article", joinColumns = @JoinColumn(name = "tag_id"), inverseJoinColumns = @JoinColumn(name = "article_id"))
	@JsonIgnore
	private Set<Article> articles = new HashSet<Article>();

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Tag() {
	}
	
	public Tag(String name, long level) {
		setName(name);
		setLevel(level);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getLevel() {
		return level;
	}

	public void setLevel(Long level) {
		this.level = level;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public Set<Problem> getProblems() {
		return problems;
	}

	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public void addArticle(Article article) {
		this.articles.add(article);
	}

	public void removeArticle(Article article) {
		this.articles.remove(article);
		
	}
	
	public void addProblem(Problem problem) {
		this.problems.add(problem);
	}

	public void removeProblem(Problem problem) {
		this.problems.remove(problem);
		
	}

	@Override
	public int compareTo(Tag o) {
		return Long.compare(this.level, o.level);
	}

}