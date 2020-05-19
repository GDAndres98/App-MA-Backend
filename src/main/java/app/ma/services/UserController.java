package app.ma.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import app.ma.entities.Course;
import app.ma.entities.Role;
import app.ma.entities.User;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepositoryDAO;
	
	@Autowired
	private CourseRepository courseRepositoryDAO;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@RequestMapping("/getAllUsers")
	public Iterable<User> getAllUsers () {
		
		Iterable<User> findAll = userRepositoryDAO.findAll();
		return findAll;
	}
	
	@RequestMapping(path="/getUserByID", method=RequestMethod.GET)
	public User getUserByID 
	(
			@RequestHeader Long id) {
		
		Optional<User> user = userRepositoryDAO.findById(id);
		if(!user.isPresent())
			return null;
		return user.get();
	}
	
	@RequestMapping(path="/getUserByUsername", method=RequestMethod.GET)
	public User getUserByUsername 
	(
			@RequestHeader String username) {
		
		User user = userRepositoryDAO.findByUsername(username);
		
		return user;
	}
	
	@RequestMapping(path="/getUserByEmail", method=RequestMethod.GET)
	public User getUserByEmail 
	(
			@RequestHeader String email) {
		
		User user = userRepositoryDAO.findByEmail(email);
		
		return user;
	}
	
	@RequestMapping(path="/getAllUserCoursesById", method=RequestMethod.GET)
	public List<Course> getAllUserCoursesById 
	(
			@RequestHeader Long id) {
		return courseRepositoryDAO.findByStudents_student_Id(id);
	}
	
	
	
	
	@RequestMapping(path="/createStudent", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addNewUser 
	(
			@RequestParam String 	firstName	, 
			@RequestParam String 	lastName	, 
			@RequestParam String 	username	, 
			@RequestParam String 	email		,
			@RequestParam String 	password	,
			@RequestParam String 	profilePic	) {
		
		if(emailIsUsed(email)) return new ResponseEntity<>("Email en uso.", HttpStatus.BAD_REQUEST);
		
		User newUser = new User(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUsername(username);
		newUser.setRole(roleRepository.findByName("student"));
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setProfilePicUrl(profilePic);
		userRepositoryDAO.save(newUser);
			
		return new ResponseEntity<>("Usuario creado correctamente.", HttpStatus.CREATED);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path="/createProfessor", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createNewProfessor 
	(
			@RequestParam String 	firstName	, 
			@RequestParam String 	lastName	, 
			@RequestParam String 	username	, 
			@RequestParam String 	email		,
			@RequestParam String 	password	,
			@RequestParam String 	profilePic	) {
		
		if(emailIsUsed(email)) return new ResponseEntity<>("Email en uso.", HttpStatus.BAD_REQUEST);
		
		User newUser = new User(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setRole(roleRepository.findByName("professor"));
		newUser.setPassword(password);
		newUser.setProfilePicUrl(profilePic);
		userRepositoryDAO.save(newUser);
			
		return new ResponseEntity<>("Usuario creado correctamente.", HttpStatus.CREATED);
	}
	
	
	
	
	
	@RequestMapping(path="/validatedUser", method=RequestMethod.POST)
	public @ResponseBody User validatedUser 
	(
			@RequestParam String username, 
			@RequestParam String password) {
		
		User user = userRepositoryDAO.findByUsernameAndPassword(username, password);
		
		return user;
	}
	
	@RequestMapping(path="/createRoles", method=RequestMethod.POST)
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	private void createRoles() {
		Role student = new Role();
		student.setName("student");
		
		roleRepository.save(student);
		
		Role professor = new Role();
		professor.setName("professor");
		professor.setProfessor(true);
	
		roleRepository.save(professor);
	}
	
	

	public boolean emailIsUsed(String email) {
		return userRepositoryDAO.countByEmail(email) != 0;
	}
	
}