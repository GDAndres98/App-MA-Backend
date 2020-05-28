package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Problem;

@Repository
public interface ProblemRepository extends CrudRepository<Problem, Long> {

	public Problem findById(long id);

}