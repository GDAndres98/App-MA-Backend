package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.TestCase;

@Repository
public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

	public TestCase findById(long id);
	public Iterable<TestCase> findByProblem_IdOrderByOrderTestCase(long id);

}