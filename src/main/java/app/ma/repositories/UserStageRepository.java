package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import app.ma.compositeKey.UserStageKey;
import app.ma.entities.UserStage;

@Repository
public interface UserStageRepository extends CrudRepository<UserStage, UserStageKey> {
	
}