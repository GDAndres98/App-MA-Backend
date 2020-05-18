package app.ma.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import app.ma.entities.*;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	public User findById(long id);
	public User findByUsername(String username);
	public User findByEmail(String email);
	public User findByUsernameAndPassword(String username, String password);

}