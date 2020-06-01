package app.ma.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Section;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {

	public Section findById(long id);
	public Iterable<Section> findByPostedAt_Id(long id);


}