package app.ma.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Level;

@Repository
public interface LevelRepository extends CrudRepository<Level, Long> {

	public Level findById(long id);
	public List<Level> findAllByOrderByNumberAsc();

}