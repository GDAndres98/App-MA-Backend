package app.ma.back;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.ma.entities.Article;
import app.ma.entities.Course;
import app.ma.entities.Role;
import app.ma.entities.User;
import app.ma.repositories.ArticleRepository;
import app.ma.repositories.CourseRepository;
import app.ma.repositories.RoleRepository;
import app.ma.repositories.UserRepository;

@Component
public class Runner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Runner.class);

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CourseRepository courseRepository;

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

		
		
		
		
		// Artículos
		ArrayList<Article> articulos = new ArrayList<Article>();
		articulos.add(new Article("Servicios RESTful", "Interner Gomez", getMarkdown("articles/1.md"),
				new Date("2020/05/18")));
		articulos.add(
				new Article("Algoritmos Avaros", "Don Cormen", getMarkdown("articles/2.md"), new Date("1998/03/12")));
		articulos.add(new Article("Grafos para ruta mas corta", "Don Cormen", "latex.md", new Date("2009/12/31")));
		articulos.add(new Article("Grafos en General", "Don Cormen", "latex.md", new Date("2011/03/12")));
		articulos.add(new Article(
				"Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo Este título es largo solo para ver cómo la pantalla al mostrarse pues... un titulo largo",
				"Don Cormen", "latex.md", new Date("1998/03/12")));
		createAllArticles(articulos);
		
		
		// Estudiantes y Profesores
		ArrayList<User> estudiantes = new ArrayList<User>();
		estudiantes.add(new User("GDAndres98", "Andrés", "Osorio", "img.png", "gd_andres98@hotmail.com", "12345"));
		estudiantes
				.add(new User("Alex_gal", "Marlon", "Estupiñán", "imgcfff.png", "maestupinan2@hotmail.com", "123123"));
		estudiantes.add(new User("nomovie2", "Ashoka", "Tano", "imgcfff.png", "nomovie2@hotmail.com", "123456"));
		createAllUsers(estudiantes, student);
		ArrayList<User> profesores = new ArrayList<User>();
		profesores.add(new User("profesor1", "Anakin", "Skywalker", "img.png", "skyani@hotmail.com", "12345"));
		profesores.add(new User("profesor2", "Kakashi", "Hatake", "imgcfff.png", "narutolore@hotmail.com", "123123"));
		createAllUsers(profesores, professor);

		ArrayList<Course> cursos = new ArrayList<Course>();
		cursos.add(new Course("Programación 1", "image1.png", profesores.get(0)));
		cursos.add(new Course("Programación 2", "image1.png", profesores.get(1)));
		createAllCourses(cursos);

	}

	private String getMarkdown(String fileName) throws IOException {
		String content = new String(Files.readAllBytes(Paths.get(fileName)));
		return content;
	}

	private void createAllArticles(ArrayList<Article> articles) {
		for (Article e : articles)
			articleRepository.save(e);
	}

	private void createAllUsers(ArrayList<User> users, Role x) {
		for (User e : users) {
			x.addUser(e);
			e.addRole(x);
			userRepository.save(e);
		}
	}

	private void createAllCourses(ArrayList<Course> courses) {
		for (Course e : courses)
			courseRepository.save(e);
	}


}
