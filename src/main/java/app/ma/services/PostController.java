package app.ma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.Post;
import app.ma.entities.User;
import app.ma.repositories.PostRepository;

@RestController
public class PostController {

	@Autowired
	private PostRepository postRepository;
	

	@RequestMapping(path="/getPostsFromCourse", method=RequestMethod.GET)
	public Iterable<Post> getPostsByCourse
	(
			@RequestHeader Long id) {
		
		Iterable<Post> posts = postRepository.findByUserCourse_Course__IdOrderByCreationDateAsc(id);
		
		return posts;
	}
	
	
	@RequestMapping(path="/createPost", method=RequestMethod.POST) 
	public @ResponseBody ResponseEntity<String> addNewPost 
	(
			@RequestParam String 	title		, 
			@RequestParam String 	content		, 
			@RequestParam Long 		userId		, 
			@RequestParam Long 		courseId	) {
		
		
		
		
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
	
}