package app.ma.services;

import java.util.BitSet;
import java.util.Date;
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

import app.ma.compositeKey.ProblemContestKey;
import app.ma.compositeKey.UserCourseKey;
import app.ma.entities.Contest;
import app.ma.entities.Course;
import app.ma.entities.Problem;
import app.ma.entities.ProblemContest;
import app.ma.entities.Role;
import app.ma.entities.Section;
import app.ma.entities.User;
import app.ma.entities.UserCourse;
import app.ma.repositories.ContestRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.ProblemContestRepository;
import app.ma.repositories.ProblemRepository;
import app.ma.repositories.SectionRepository;
import app.ma.repositories.UserCourseRepository;
import app.ma.repositories.UserRepository;

@RestController
public class CourseController {

	@Autowired private CourseRepository courseRepository;
	@Autowired private UserRepository userRepository;
	@Autowired private UserCourseRepository userCourseRepository;
	@Autowired private SectionRepository sectionRepository;
	@Autowired private ContestRepository contestRepository;
	@Autowired private ProblemRepository problemRepository;
	@Autowired private ProblemContestRepository problemContestRepository;
	
	@CrossOrigin
	@RequestMapping("/getAllCourses")
	public Iterable<Course> getAllCourses() {
		Iterable<Course> findAll = courseRepository.findAll();
		for(Course e: findAll)
			System.out.println(e.getProfessor().getFirstName());
		return findAll;
	}
	
	@CrossOrigin
	@RequestMapping(path = "/getCourseById", method = RequestMethod.GET)
	public ResponseEntity<Course> getCourseById(@RequestHeader Long id) {
		Optional<Course> course = courseRepository.findById(id);
		if(!course.isPresent()) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(course.get(), HttpStatus.OK);
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
		
		
		Contest contest = new Contest();
		contest.setEndTime(new Date());
		contest.setStartTime(new Date());
		contest.setName(name);
		contest.setPrivate(true);
		contest.setVisible(false);
		contest.setPartialVerdict(false);
		
		this.contestRepository.save(contest);
		
		course.setContest(contest);
		
		courseRepository.save(course);
		
		return new ResponseEntity<>("Curso \""+ name + "\" creado satisfactoriamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/editCourse", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> editCourse
	(
			@RequestParam String 	name	, 
			@RequestParam String 	image	, 
			@RequestParam Long 		professorId,
			@RequestParam Long 		courseId) {
		
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if(!opCourse.isPresent())
			return new ResponseEntity<>("Curso no existe.", HttpStatus.BAD_REQUEST);
		
		Optional<User> opProfessor = userRepository.findById(professorId);
		if(!opProfessor.isPresent())
			return new ResponseEntity<>("Usuario no existe.", HttpStatus.BAD_REQUEST);
		User professor = opProfessor.get();
		
		boolean haveRole = false;
		for(Role r: professor.getRole())
			haveRole |= r.isProfessor();
		if(!haveRole)
			return new ResponseEntity<>("Usuario no es profesor.", HttpStatus.UNAUTHORIZED);
		Course course = opCourse.get();
		course.setName(name);
		course.setLogoUrl(image);
		course.setProfessor(professor);
		
		courseRepository.save(course);
		return new ResponseEntity<>("Curso \""+ name + "\" actualizado satisfactoriamente.", HttpStatus.OK);
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
	@RequestMapping(path="/removeStudentToCourse", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> removeStudentToCourse
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
		if(!userCourseRepository.existsById(key))
			return new ResponseEntity<>("El estudiante no está en la clase.", HttpStatus.CONFLICT);
		
		userCourseRepository.deleteById(key);

		return new ResponseEntity<>("Estudiante removido satisfactoriamente de la clase.", HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(path="/addHomework", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addHomework
	(
			@RequestParam Long 	contestId, 
			@RequestParam Long 	problemId,
			@RequestParam Date 	limitDate,
			@RequestParam List<Boolean> 	testCases) {
		
		Optional<Contest> opContest = contestRepository.findById(contestId);
		if(!opContest.isPresent()) return new ResponseEntity<>("Competencia no existe.", HttpStatus.BAD_REQUEST);
		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if(!opProblem.isPresent()) return new ResponseEntity<>("Problema no existe.", HttpStatus.BAD_REQUEST);

		Contest contest = opContest.get();
		Problem problem = opProblem.get();
		
		int i = 0;
		long bit = 0;
		for(Boolean e: testCases) {			
			if(!e)
				bit |= (1 << i);
			i++;
		}
		
		ProblemContest problemContest = new ProblemContest();
		problemContest.setId(new ProblemContestKey(problemId, contestId));
		problemContest.setProblem(problem);
		problemContest.setContest(contest);
		problemContest.setLimitDate(limitDate);
		problemContest.setProblemTestCases(bit);
		
		this.problemContestRepository.save(problemContest);
		return new ResponseEntity<>("Tarea guardada exitosamente.", HttpStatus.CREATED);
	}
	
	@CrossOrigin
	@RequestMapping(path="/editHomework", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> editHomework
	(
			@RequestParam Long 	contestId, 
			@RequestParam Long 	problemId,
			@RequestParam Date 	limitDate) {
		
		Optional<Contest> opContest = contestRepository.findById(contestId);
		if(!opContest.isPresent()) return new ResponseEntity<>("Competencia no existe.", HttpStatus.BAD_REQUEST);
		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if(!opProblem.isPresent()) return new ResponseEntity<>("Problema no existe.", HttpStatus.BAD_REQUEST);
		
		ProblemContest problemContest = problemContestRepository.findById(new ProblemContestKey(problemId, contestId)).get();
		problemContest.setLimitDate(limitDate);
		
		this.problemContestRepository.save(problemContest);
		return new ResponseEntity<>("Tarea editada exitosamente.", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/deleteHomework", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> deleteHomework
	(
			@RequestParam Long 	contestId, 
			@RequestParam Long 	problemId) {
		
		Optional<Contest> opContest = contestRepository.findById(contestId);
		if(!opContest.isPresent()) return new ResponseEntity<>("Competencia no existe.", HttpStatus.BAD_REQUEST);
		Optional<Problem> opProblem = problemRepository.findById(problemId);
		if(!opProblem.isPresent()) return new ResponseEntity<>("Problema no existe.", HttpStatus.BAD_REQUEST);
		
		this.problemContestRepository.deleteById(new ProblemContestKey(problemId, contestId));
		return new ResponseEntity<>("Tarea eliminada exitosamente.", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path = "/getCourseStudentsById", method = RequestMethod.GET)
	public List<User> getAllCourseStudentsById(@RequestHeader Long id) {
		return userRepository.findByCourses_Course_Id(id);
	}
	
	@CrossOrigin
	@RequestMapping(path="/addSectionToCourse", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addSectionToCourse
	(
			@RequestParam Long 		sectionId,  
			@RequestParam Long 		courseId) {
		Optional<Section> opSection = sectionRepository.findById(sectionId);
		if(!opSection.isPresent()) return new ResponseEntity<>("Sección no existe.", HttpStatus.BAD_REQUEST);
		Optional<Course> opCourse = courseRepository.findById(courseId);
		if(!opCourse.isPresent()) return new ResponseEntity<>("Curso no existe.", HttpStatus.BAD_REQUEST);
		Course course = opCourse.get();
		Section section = opSection.get();
		
		course.addSection(section);
		section.setPostedAt(course);
		
		courseRepository.save(course);

		return new ResponseEntity<>("Sección Agregada correctamente.", HttpStatus.ACCEPTED);
	}
	
	@CrossOrigin
	@RequestMapping(path = "/getProfesorCourseById", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Course>> getProfesorCourseById(@RequestHeader Long id) {
		List<Course> courses = this.courseRepository.findByProfessor_Id(id);
		if(courses == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		return new ResponseEntity<Iterable<Course>>(courses, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(path="/hasCoursePermision", method=RequestMethod.GET)
	public Boolean hasCoursePermision
	(
			@RequestHeader Long courseId,
			@RequestHeader Long userId) {
		Optional<Course> opCourse = this.courseRepository.findById(courseId);
		if(!opCourse.isPresent()) return false;
		return opCourse.get().getProfessor().getId() == userId;
	}
	
	
	
	
	
	
	
}
