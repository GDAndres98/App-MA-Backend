package app.ma.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import app.ma.entities.*;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {

	public Course findById(long id);
	public List<Course> findByStudents_student_Id(long id);
	public List<Course> findByProfessor_Id(long id);

}