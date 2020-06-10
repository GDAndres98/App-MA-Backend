package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Article;
import app.ma.entities.Problem;

@Repository
public interface ProblemRepository extends PagingAndSortingRepository<Problem, Long> {

	public Problem findById(long id);

}