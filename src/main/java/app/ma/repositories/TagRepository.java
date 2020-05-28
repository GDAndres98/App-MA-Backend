package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Section;
import app.ma.entities.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {

	public Tag findById(long id);
	public Iterable<Tag> findByLevel(long level);

}