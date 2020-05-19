package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.compositeKey.UserCourseKey;
import app.ma.entities.*;

@Repository
public interface UserCourseRepository extends CrudRepository<UserCourse, UserCourseKey> {
	
}