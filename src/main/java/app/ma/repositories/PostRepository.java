package app.ma.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Post;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
	public Page<Post> findByUserCourse_Course_IdAndParentIsNullOrderByCreationDateDesc(Long id, Pageable paging);
	public Page<Post> findByParent_IdOrderByCreationDateAsc(Long id, Pageable paging);
}