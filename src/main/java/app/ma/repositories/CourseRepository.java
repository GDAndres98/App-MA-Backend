package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import app.ma.entities.*;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

	public Course findById(long id);
}