package app.ma.services;

import java.util.Observable;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import app.ma.entities.Course;
import app.ma.entities.User;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.UserRepository;

@RestController
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/getAllCourses")
	public Iterable<Course> getAllCourses() {
		Iterable<Course> findAll = courseRepository.findAll();
		return findAll;
	}
	
	@RequestMapping(path = "/getCourseById", method = RequestMethod.GET)
	public Course getCourseById(@RequestHeader Long id) {
		Optional<Course> course = courseRepository.findById(id);
		if(!course.isPresent()) return null;
		return course.get();
	}
	

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(path="/createCourse", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> createCourse
	(
			@RequestParam String 	name	, 
			@RequestParam String 	image	, 
			@RequestParam Long 		professorId) {
		
		Optional<User> opProfessor = userRepository.findById(professorId);
		if(!opProfessor.isPresent())
			return new ResponseEntity<>("Usuario no existe.", HttpStatus.BAD_REQUEST);
		User professor = opProfessor.get();
		if(!professor.getRole().isProfessor())
			return new ResponseEntity<>("Usuario no es profesor.", HttpStatus.UNAUTHORIZED);
		Course course = new Course();
		course.setName(name);
		course.setLogoUrl(image);
		course.setProfessor(professor);
		
		courseRepository.save(course);
		return new ResponseEntity<>("Curso \""+ name + "\" creado satisfactoriamente.", HttpStatus.CREATED);
	}
	
	
	
	
	
	
}
