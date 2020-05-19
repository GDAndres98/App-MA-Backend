package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import app.ma.entities.*;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	public Role findById(long id);
	public Role findByName(String name);
}