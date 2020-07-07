package app.ma.repositories;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.annotation.JsonFormat;

import app.ma.entities.Article;
import app.ma.entities.Contest;
import app.ma.entities.Post;
import app.ma.entities.Tag;

@Repository
public interface ContestRepository extends CrudRepository<Contest, Long> {
	// Corriendo
	public List<Contest> findByStartTimeLessThanAndEndTimeGreaterThanAndIsVisibleIsTrue(Date date1, Date date2);
	
	//Futuras
	public List<Contest> findByStartTimeGreaterThanAndIsVisibleIsTrue(Date date1);
	
	// Pasadas
	public Page<Contest> findByEndTimeLessThanAndIsVisibleIsTrue(Date date1, Pageable paging);
	
}