package app.ma.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.ma.entities.Article;
import app.ma.entities.Level;
import app.ma.entities.Stage;
import app.ma.entities.Tag;

@Repository
public interface StageRepository extends PagingAndSortingRepository<Stage, Long> {

	public Stage findById(long id);
	public List<Stage> findAllByOrderByNumberAsc();
	
}