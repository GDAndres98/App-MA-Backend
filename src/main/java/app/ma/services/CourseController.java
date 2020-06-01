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
import org.springframework.web.bind.annotation.RestController;

import app.ma.compositeKey.UserCourseKey;
import app.ma.entities.Course;
import app.ma.entities.Role;
import app.ma.entities.User;
import app.ma.entities.UserCourse;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.UserCourseRepository;
import app.ma.repositories.UserRepository;

@RestController
public class CourseController {

	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserCourseRepository userCourseRepository;
	
	@CrossOrigin
	@RequestMapping("/getAllCourses")
	public Iterable<Course> getAllCourses() {
		Iterable<Course> findAll = courseRepository.findAll();
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path = "/getCourseById", method = RequestMethod.GET)
	public Course getCourseById(@RequestHeader Long id) {
		Optional<Course> course = courseRepository.findById(id);
		if(!course.isPresent()) return null;
		return course.get();
	}
	
	@CrossOrigin
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
		
		boolean haveRole = false;
		for(Role r: professor.getRole())
			haveRole |= r.isProfessor();
		if(!haveRole)
			return new ResponseEntity<>("Usuario no es profesor.", HttpStatus.UNAUTHORIZED);
		Course course = new Course();
		course.setName(name);
		course.setLogoUrl(image);
		course.setProfessor(professor);
		
		courseRepository.save(course);
		return new ResponseEntity<>("Curso \""+ name + "\" creado satisfactoriamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/addStudentToCourse", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addStudentToCourse
	(
			@RequestParam Long 	studentId, 
			@RequestParam Long 	courseId) {
		
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if(!opCourse.isPresent()) return new ResponseEntity<>("Curso no existe.", HttpStatus.BAD_REQUEST);
		Optional<User> opStudent  = userRepository.findById(studentId);
		if(!opStudent.isPresent()) return new ResponseEntity<>("Estudiante no existe.", HttpStatus.BAD_REQUEST);

		Course course = opCourse.get();
		User student  = opStudent.get();
		
		boolean haveRole = false;
		for(Role r: student.getRole())
			haveRole |= r.isStudent();
		
		if(!haveRole)  
			return new ResponseEntity<>("Usuario no es un estudiante.", HttpStatus.UNAUTHORIZED);
		
		UserCourseKey key = new UserCourseKey(course.getId(), student.getId());
		if(userCourseRepository.existsById(key))
			return new ResponseEntity<>("El estudiante ya está en la clase.", HttpStatus.CONFLICT);
		UserCourse userCourse = new UserCourse();
		userCourse.setId(key);
		userCourse.setCourse(course);
		userCourse.setStudent(student);
		
		userCourseRepository.save(userCourse);

		return new ResponseEntity<>("Estudiante añadido satisfactoriamente a la clase.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path = "/getCourseStudentsById", method = RequestMethod.GET)
	public List<User> getAllCourseStudentsById(@RequestHeader Long id) {
		return userRepository.findByCourses_Course_Id(id);
	}
	
	
	
	
	
	
	
	
	
}
