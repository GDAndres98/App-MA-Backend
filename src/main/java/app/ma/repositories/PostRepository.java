package app.ma.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.*;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
<<<<<<< HEAD
	
	public List<Post> findByUserCourse_Course_IdOrderByCreationDateAsc(Long id);
=======
	public List<Post> findByUserCourse_Course_IdAndParentIsNullOrderByCreationDateAsc(Long id);
>>>>>>> branch 'master' of https://github.com/GDAndres98/App-MA-Backend.git
}