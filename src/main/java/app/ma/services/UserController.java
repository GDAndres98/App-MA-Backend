package app.ma.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.Course;
import app.ma.entities.Role;
import app.ma.entities.User;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@CrossOrigin
	@RequestMapping("/getAllUsers")
	public Iterable<User> getAllUsers () {
		
		Iterable<User> findAll = userRepository.findAll();
		return findAll;
	}

	@CrossOrigin
	@RequestMapping("/getAllRole")
	public Iterable<Role> getAllRole () {
		
		Iterable<Role> findAll = roleRepository.findAll();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getUserById", method=RequestMethod.GET)
	public ResponseEntity<User> getUserByID 
	(
			@RequestHeader Long id) {
		
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(user.get(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/getUserByUsername", method=RequestMethod.GET)
	public User getUserByUsername 
	(
			@RequestHeader String username) {
		
		User user = userRepository.findByUsername(username);
		
		return user;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getUserByEmail", method=RequestMethod.GET)
	public User getUserByEmail 
	(
			@RequestHeader String email) {
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}
	
	@CrossOrigin
	@RequestMapping(path="/getAllUserCoursesById", method=RequestMethod.GET)
	public List<Course> getAllUserCoursesById 
	(
			@RequestHeader Long id) {
		return courseRepository.findByStudents_student_Id(id);
	}
	
	@CrossOrigin
	@RequestMapping(path="/isAdmin", method=RequestMethod.GET)
	public Boolean idAdmin
	(
			@RequestHeader Long id) {
		Optional<User> userOp = this.userRepository.findById(id);
		if(!userOp.isPresent()) return false;
		return userOp.get().getAdmin();
	}	
	@CrossOrigin
	@RequestMapping(path="/isProfesor", method=RequestMethod.GET)
	public Boolean isProfesor
	(
			@RequestHeader Long id) {
		Optional<User> userOp = this.userRepository.findById(id);
		if(!userOp.isPresent()) return false;
		return userOp.get().isProfesor();
	}
	
	@CrossOrigin
	@RequestMapping(path="/getProfesorById", method=RequestMethod.GET)
	public ResponseEntity<User> getProfesorById 
	(
			@RequestHeader Long id) {
		
		Optional<User> profesorOp = this.userRepository.findById(id);
		if(!profesorOp.isPresent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		User profesor = profesorOp.get();
		
		if(!profesor.isProfesor())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(profesor, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/getStudentById", method=RequestMethod.GET)
	public ResponseEntity<User> getStudentById 
	(
			@RequestHeader Long id) {
		
		Optional<User> studentOp = this.userRepository.findById(id);
		if(!studentOp.isPresent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		User student = studentOp.get();
		
		if(!student.isStudent())
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@CrossOrigin
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
		if(usernameIsUsed(username)) return new ResponseEntity<>("Nombre de usuario en uso.", HttpStatus.BAD_REQUEST);
		User newUser = new User(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUsername(username);
		Role role = roleRepository.findByName("student");
		newUser.addRole(role);
		role.addUser(newUser);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setProfilePicUrl(profilePic);
		userRepository.save(newUser);

		
		return new ResponseEntity<>("Usuario creado correctamente.", HttpStatus.CREATED);
	}
	
	

	@CrossOrigin
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
		if(usernameIsUsed(username)) return new ResponseEntity<>("Nombre de usuario en uso.", HttpStatus.BAD_REQUEST);

		User newUser = new User(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUsername(username);
		newUser.setEmail(email);
		Role role = roleRepository.findByName("professor");
		newUser.addRole(role);
		role.addUser(newUser);
		newUser.setPassword(password);
		newUser.setProfilePicUrl(profilePic);
		userRepository.save(newUser);
			
		return new ResponseEntity<>("Usuario creado correctamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/setProfesorRole", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> setProfesorRol 
	(
			@RequestParam Long 	 userId, 
			@RequestParam Boolean isProfesor) {

		Optional<User> opUser = userRepository.findById(userId);
		if(!opUser.isPresent())
			return new ResponseEntity<>("Usuario no existe.", HttpStatus.BAD_REQUEST);
		Role profesor = roleRepository.findByName("professor");
		User user = opUser.get();
		if(isProfesor && !user.isProfesor()) {
			user.addRole(profesor);
			profesor.addUser(user);
		}
		else if(!isProfesor && user.isProfesor()) {
			user.removeRole(profesor);
			profesor.removeUser(user);
		}
		userRepository.save(user);
		roleRepository.save(profesor);

		return new ResponseEntity<>("Usuario actualizado correctamente.", HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(path="/validatedUser", method=RequestMethod.POST)
	public @ResponseBody User validatedUser 
	(
			@RequestParam String username, 
			@RequestParam String password) {
		
		User user = userRepository.findByUsernameAndPassword(username, password);
		
		return user;
	}
	
	private void createRoles() {
		Role student = new Role();
		student.setName("student");
		student.setStudent(true);
		
		roleRepository.save(student);
		
		Role professor = new Role();
		professor.setName("professor");
		professor.setProfessor(true);
	
		roleRepository.save(professor);
	}
	
	

	public boolean emailIsUsed(String email) {
		return userRepository.countByEmail(email) != 0;
	}
	
	private boolean usernameIsUsed(String username) {
		return userRepository.countByUsername(username) != 0;
	}
}