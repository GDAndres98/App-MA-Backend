package app.ma.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import app.ma.entities.*;

@Repository
public interface SectionRepository extends CrudRepository<Section, Long> {

	public Section findById(long id);
	public Iterable<Section> findByPostedAt_Id(long id);


}