package app.ma.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.ma.entities.Article;
import app.ma.entities.Tag;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {

	public Article findById(long id);

	public List<Article> findFirst5ByTitleStartsWithIgnoreCaseOrderByTitleAsc(String title);

	@Query("select i from Article i join i.tags t where t in :tags group by i.id having count(i.id) = :tagCount")
	public Page<Article> findAllArticlesWithTags(@Param("tags") Set<Tag> tags, @Param("tagCount") Long numberOfTags,
			Pageable paging);

}