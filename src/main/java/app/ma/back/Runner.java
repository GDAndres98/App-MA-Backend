package app.ma.back;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.ma.entities.Role;
import app.ma.repositories.RoleRepository;

@Component
public class Runner implements CommandLineRunner {
	
    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void run(String... args) throws Exception {
		Role student = new Role();
		student.setName("student");
		student.setStudent(true);
		
		roleRepository.save(student);
		
		Role professor = new Role();
		professor.setName("professor");
		professor.setProfessor(true);
	
		roleRepository.save(professor);
	}

}
