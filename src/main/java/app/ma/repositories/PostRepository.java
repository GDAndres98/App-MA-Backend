package app.ma.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	public List<Post> findByUserCourse_Course_IdAndParentIsNullOrderByCreationDateAsc(Long id);
}