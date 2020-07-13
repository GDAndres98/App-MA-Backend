package app.ma.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "article")
public class Article {

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
	private Date dateWritten;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "articles")
	@OrderBy(value = "level ASC")
	private Set<Tag> tags = new HashSet<Tag>();

	@ManyToMany(mappedBy = "articles", fetch = FetchType.EAGER)
	@JsonIgnore
	private Set<Section> sections = new HashSet<Section>();

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "level", nullable = true) // TODO 
	private Level level = null;

	@CreationTimestamp
	private Date createAt;
	@UpdateTimestamp
	private Date updateAt;

	// ****************************************************************

	// -------------------------------------------------------------------

	public Article() {
	}
	
	public Article(String title, String author, String markdownURL, Date dateWritten) {
		setTitle(title);
		setAuthor(author);
		setMarkdown(markdownURL);
		setDateWritten(dateWritten);
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

	public void setMarkdown(String markdown) {
		this.markdown = markdown;
	}

	public Date getDateWritten() {
		return dateWritten;
	}

	public void setDateWritten(Date dateWritten) {
		this.dateWritten = dateWritten;
	}

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void addSection(Section section) {
		this.sections.add(section);
		
	}
	
	public void addTag(Tag tag) {
		this.tags.add(tag);
		
	}

	public void removeTag(Tag tag) {
		this.tags.remove(tag);
	}

}