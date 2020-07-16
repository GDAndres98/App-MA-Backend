package app.ma.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.entities.Level;

@Repository
public interface LevelRepository extends CrudRepository<Level, Long> {

	public Level findById(long id);
	public Optional<Level> findByStage_IdAndNumber(Long id, Long number);
	public List<Level> findAllByOrderByNumberAsc();
	public List<Level> findByStage_IdOrderByNumberAsc(long id);
	public Long countByStage_Id(long id);

}