package app.ma.repositories;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Article;

@Repository
public interface ArticleRepository  extends PagingAndSortingRepository<Article, Long> {

	public Article findById(long id);
	public List<Article> findFirst5ByTitleStartsWithIgnoreCaseOrderByTitleAsc(String title);

}