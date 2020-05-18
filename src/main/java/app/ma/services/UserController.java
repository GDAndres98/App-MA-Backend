package app.ma.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.ma.entities.User;
import app.ma.repositories.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepositoryDAO;
	
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
	
	@RequestMapping(path="/addUser", method=RequestMethod.POST) 
	public @ResponseBody String addNewUser 
	(
			@RequestParam String 	firstName	, 
			@RequestParam String 	lastName	, 
			@RequestParam String 	username	, 
			@RequestParam String 	email		,
			@RequestParam String 	password	,
			@RequestParam String 	profilePic	) {
		
		User newUser = new User(username);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setUsername(username);
		newUser.setEmail(email);
		newUser.setPassword(password);
		newUser.setProfilePicUrl(profilePic);
		userRepositoryDAO.save(newUser);
		return "Usuario Guardado";
	}
	
	@RequestMapping(path="/validatedUser", method=RequestMethod.POST)
	public @ResponseBody User validatedUser 
	(
			@RequestParam String username, 
			@RequestParam String password) {
		
		User user = userRepositoryDAO.findByUsernameAndPassword(username, password);
		
		return user;
	}
	
	
}