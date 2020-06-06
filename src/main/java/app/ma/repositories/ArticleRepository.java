package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Article;

@Repository
public interface ArticleRepository  extends PagingAndSortingRepository<Article, Long> {

	public Article findById(long id);

}