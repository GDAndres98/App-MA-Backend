package app.ma.services;

import java.util.LinkedList;
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
import app.ma.entities.Post;
import app.ma.entities.User;
import app.ma.entities.UserCourse;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.PostRepository;
import app.ma.repositories.UserCourseRepository;
import app.ma.repositories.UserRepository;

@RestController
public class PostController {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserCourseRepository userCourseRepository;

	@CrossOrigin
	@RequestMapping(path = "/getPostsFromCourse", method = RequestMethod.GET)
	public Iterable<Post> getPostsFromCourse(@RequestHeader Long id) {

		Iterable<Post> posts = postRepository.findByUserCourse_Course_IdAndParentIsNullOrderByCreationDateAsc(id);
		
		return posts;
	}

	@CrossOrigin
	@RequestMapping(path = "/getSubPostFromPost", method = RequestMethod.GET)
	public Iterable<Post> getSubPostFromPost(
			@RequestHeader Long id) {
		
		LinkedList<Post> posts = new LinkedList<Post>();
		
		Optional<Post> opPost = postRepository.findById(id);
		if (!opPost.isPresent())
			return posts;
		System.out.println();
		Post child = opPost.get();
		while((child = child.getChild()) != null)
			posts.add(child);

		return posts;
	}

	@CrossOrigin
	@RequestMapping(path = "/createPost", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addNewPost(
			@RequestParam String title,
			@RequestParam String content,
			@RequestParam Long userId,
			@RequestParam Long courseId) {

		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (!opCourse.isPresent())
			return new ResponseEntity<>("Curso no existe.", HttpStatus.BAD_REQUEST);
		Optional<User> opStudent = userRepository.findById(userId);
		if (!opStudent.isPresent())
			return new ResponseEntity<>("Usuario no existe.", HttpStatus.BAD_REQUEST);

		Course course = opCourse.get();
		User student = opStudent.get();

		UserCourseKey key = new UserCourseKey(course.getId(), student.getId());

		if (!userCourseRepository.existsById(key))
			return new ResponseEntity<>("El Usuario no está asociado a la clase", HttpStatus.CONFLICT);

		Optional<UserCourse> opCourseStudent = userCourseRepository.findById(key);

		Post post = new Post();
		post.setTitle(title);
		post.setContent(content);
		post.setUserCourse(opCourseStudent.get());

		postRepository.save(post);

		return new ResponseEntity<>("Post añadido satisfactoriamente al foro", HttpStatus.CREATED);
	}

	@CrossOrigin
	@RequestMapping(path = "/createSubPost", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> addNewSubPost(
			@RequestParam String content, 
			@RequestParam Long postId,
			@RequestParam Long userId,
			@RequestParam Long courseId) {

		Optional<Course> opCourse = courseRepository.findById(courseId);
		if (!opCourse.isPresent())
			return new ResponseEntity<>("Curso no existe.", HttpStatus.BAD_REQUEST);
		Optional<User> opStudent = userRepository.findById(userId);
		if (!opStudent.isPresent())
			return new ResponseEntity<>("Usuario no existe.", HttpStatus.BAD_REQUEST);
		Optional<Post> opPost = postRepository.findById(postId);
		if (!opPost.isPresent())
			return new ResponseEntity<>("Post no existe.", HttpStatus.BAD_REQUEST);

		Course course = opCourse.get();
		User student = opStudent.get();
		Post post = opPost.get();

		UserCourseKey key = new UserCourseKey(course.getId(), student.getId());

		Optional<UserCourse> opCourseStudent = userCourseRepository.findById(key);

		Post reply = new Post();
		reply.setTitle("Reply");
		reply.setContent(content);
		reply.setParent(post);
		reply.setUserCourse(opCourseStudent.get());
		post.setChild(reply);
		postRepository.save(reply);
		postRepository.save(post);

		return new ResponseEntity<>("Reply añadido satisfactoriamente al Post", HttpStatus.CREATED);
	}

}