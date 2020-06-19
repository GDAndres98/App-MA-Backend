package app.ma.entities;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "problem")
public class Problem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;
	@Column(nullable = false)
	private String author;
	@Column(nullable = false, length = 4000)
	private String markdown;
	@Column(nullable = false)
	private Long timeLimit;
	@Column(nullable = false)
	private Long memoryLimit;

	// TODO Problem-Contest
	@OneToMany(mappedBy = "problem")
	@JsonIgnore
	Set<ProblemContest> problemContest = new HashSet<>();

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "problems")
	@OrderBy(value = "level ASC")
	private Set<Tag> tags = new HashSet<Tag>();

	@JsonIgnore
    @OneToMany(mappedBy="problem", cascade = CascadeType.REMOVE)
    private Set<TestCase> testCases;
	
	@CreationTimestamp
	private Date createAt;

	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Problem() {

	}
	
	public Problem(String title, String author, String markdown, Long timeLimit, Long memoryLimit) {
		this.title = title;
		this.author = author;
		this.markdown = markdown;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getMarkdown() {
		return markdown;
	}

	public void setMarkdown(String markdownl) {
		this.markdown = markdownl;
	}

	public Long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Long getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(Long memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	
	public Set<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(Set<TestCase> testCases) {
		this.testCases = testCases;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void addTag(Tag tag) {
		this.tags.add(tag);

	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);
	}

	public void addProblemContest(ProblemContest problemContest) {
		this.problemContest.add(problemContest);
	}

	public Set<ProblemContest> getProblemContest() {
		return problemContest;
	}

	public void setProblemContest(Set<ProblemContest> problemContest) {
		this.problemContest = problemContest;
	}
}