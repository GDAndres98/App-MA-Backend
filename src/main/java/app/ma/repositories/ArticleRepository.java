package app.ma.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import app.ma.entities.*;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

	public Article findById(long id);

}