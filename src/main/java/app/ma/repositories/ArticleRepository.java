package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

	public Article findById(long id);

}